package com.tpopdsunomas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.tpopdsunomas.service.CuentaService;
import com.tpopdsunomas.service.DeporteService;
import com.tpopdsunomas.service.EmailService;
import com.tpopdsunomas.service.PartidoService;
import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.Deporte;
import com.tpopdsunomas.model.Localizador;
import com.tpopdsunomas.model.Partido;
import com.tpopdsunomas.patterns.observer.EmailNotificacion;
import com.tpopdsunomas.patterns.repo.CuentaRepoLocal;
import com.tpopdsunomas.patterns.repo.DeporteRepoLocal;
import com.tpopdsunomas.patterns.repo.ICuentaRepository;
import com.tpopdsunomas.patterns.repo.IDeporteRepository;
import com.tpopdsunomas.patterns.repo.IPartidoRepository;
import com.tpopdsunomas.patterns.repo.PartidoRepoLocal;
import com.tpopdsunomas.patterns.state.*;
import com.tpopdsunomas.patterns.strategy.EmparejamientoPorNivel;
import com.tpopdsunomas.patterns.strategy.IStrategyEmparejamiento;
import com.tpopdsunomas.patterns.strategyNivel.INivelJugador;
import com.tpopdsunomas.patterns.strategyNivel.Principiante;

public class Main {
    // 1. Creamos los Repositorios (la capa de "Base de Datos" en memoria)
    private static ICuentaRepository cuentaRepo = new CuentaRepoLocal();
    private static IPartidoRepository partidoRepo = new PartidoRepoLocal();
    private static IDeporteRepository deporteRepo = new DeporteRepoLocal(); // (Deberás crear este repo)

    // 2. Creamos los Servicios (la capa de "Lógica de Negocio")
    private static CuentaService cuentaService = new CuentaService(cuentaRepo);
    private static PartidoService partidoService = new PartidoService(partidoRepo, cuentaRepo);
    private static DeporteService deporteService = new DeporteService(deporteRepo); // (Y este servicio)

    // 3. Herramientas del Main (Vista/Controlador de consola)
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║   SISTEMA UNO MAS - ARQUITECTURA LIMPIA  ║");
        System.out.println("╚═══════════════════════════════════════════╝\n");

        cargarDatosEjemplo();

        boolean continuar = true;
        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    crearPartido();
                    break;
                case 3:
                    unirseAPartido();
                    break;
                case 4:
                    // Corregido: El menú debe ser de ACCIONES, no de estados
                    gestionarEstadoPartido();
                    break;
                case 5:
                    // Corregido: La estrategia es para BUSCAR partidos (RF2)
                    buscarPartidosConEstrategia();
                    break;
                case 6:
                    enviarNotificacion();
                    break;
                case 7:
                    mostrarPartidos();
                    break;
                case 8:
                    mostrarUsuarios();
                    break;
                case 9:
                    probarEnvioEmail();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("\n¡Gracias por usar el Sistema Uno Mas!");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
        scanner.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n════════════ MENÚ PRINCIPAL ════════════");
        System.out.println("1.  Registrar nuevo usuario");
        System.out.println("2.  Crear nuevo partido");
        System.out.println("3.  Unirse a un partido");
        System.out.println("4.  Gestionar estado de partido (Confirmar, Iniciar, etc.)");
        System.out.println("5.  Buscar partidos (Patrón Strategy)");
        System.out.println("6.  Forzar notificación de partido (Patrón Observer)");
        System.out.println("7.  Ver todos los partidos");
        System.out.println("8.  Ver todos los usuarios");
        System.out.println("9.  🔥 PROBAR ENVÍO DE EMAIL REAL (Patrón Adapter)");
        System.out.println("0.  Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void registrarUsuario() {
        System.out.println("\n═══ REGISTRAR NUEVO USUARIO ═══");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String clave = scanner.nextLine();
        System.out.print("Puntos de Nivel (ej: 0=Principiante, 15=Intermedio, 25=Avanzado): ");
        int puntosNivel = leerOpcion();
        
        // --- AHORA ---
        // El Main solo recolecta datos y llama al servicio.
        try {
            Cuenta nuevaCuenta = cuentaService.registrarCuenta(nombre, email, clave, puntosNivel);
            System.out.println("\n✓ Usuario registrado exitosamente: " + nuevaCuenta);
            System.out.println("Nivel asignado: " + nuevaCuenta.getNivel().getNombre());
        } catch (Exception e) {
            System.out.println("⚠ Error al registrar: " + e.getMessage());
        }
    }

    private static void crearPartido() {
        System.out.println("\n═══ CREAR NUEVO PARTIDO ═══");

        // --- AHORA ---
        // 1. Pedimos los usuarios al servicio
        List<Cuenta> usuarios = cuentaService.obtenerTodasLasCuentas();
        if (usuarios.isEmpty()) {
            System.out.println("⚠ No hay usuarios registrados. Registre un usuario primero.");
            return;
        }

        System.out.println("\n--- Seleccionar organizador ---");
        usuarios.forEach(u -> System.out.println(u.getId() + ". " + u.getNombre()));
        System.out.print("ID del organizador: ");
        int idOrganizador = leerOpcion();
        
        // (Validar que el usuario exista...)

        System.out.println("\n--- Deporte ---");
        // 2. Pedimos los deportes al servicio
        List<Deporte> deportes = deporteService.obtenerTodos();
        deportes.forEach(d -> System.out.println(d.getId() + ". " + d.getNombre()));
        System.out.print("ID del deporte: ");
        int idDeporte = leerOpcion();
        Deporte deporteSeleccionado = deporteService.buscarPorId(idDeporte).orElse(null);

        if (deporteSeleccionado == null) {
            System.out.println("⚠ Deporte no encontrado");
            return;
        }

        System.out.print("\nCantidad de jugadores requeridos (ej: 10): ");
    int cantJugadores = leerOpcion();

        System.out.println("\n--- Nivel Requerido para el Partido ---");
    System.out.println("1. Principiante");
    System.out.println("2. Intermedio");
    System.out.println("3. Avanzado");
    System.out.println("4. Para todos (Sin filtro de nivel)");
    System.out.print("Seleccione nivel: ");
    int opNivel = leerOpcion();

    INivelJugador nivelRequerido = null;
    
    if (opNivel >= 1 && opNivel <= 3) {

        int puntos = 0;
        if (opNivel == 1) puntos = 5;   // (Principiante)
        if (opNivel == 2) puntos = 15;  // (Intermedio)
        if (opNivel == 3) puntos = 25;  // (Avanzado)
        
        Cuenta dummy = new Cuenta(0, "dummy", "dummy", "dummy", puntos);
        nivelRequerido = dummy.getNivel();
        
        System.out.println("Nivel seleccionado: " + nivelRequerido.getNombre());
    } else {
        System.out.println("Nivel seleccionado: Para todos");
    }

        System.out.print("Horario (dd/MM/yyyy HH:mm): ");
        String horarioStr = scanner.nextLine();
        LocalDateTime fechaHora = LocalDateTime.parse(horarioStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        
        // 3. El Main llama al servicio para crear el partido
        Partido partido = partidoService.crearPartido(
            idOrganizador, 
            deporteSeleccionado, 
            cantJugadores,
            fechaHora,
            nivelRequerido
        );

        System.out.println("\n✓ Partido creado exitosamente: " + partido.getId());
        System.out.println("Estado inicial: " + partido.getEstado().getNombre());
    }

    private static void unirseAPartido() {
        System.out.println("\n═══ UNIRSE A UN PARTIDO ═══");

        // --- AHORA ---
        // 1. Pedir partidos al servicio
        List<Partido> partidos = partidoService.obtenerTodosLosPartidos();
        if (partidos.isEmpty()) { System.out.println("⚠ No hay partidos."); return; }
        
        System.out.println("Partidos disponibles (que necesitan jugadores):");
        partidos.stream()
            .filter(p -> p.getEstado().getNombre().equals("Necesita Jugadores"))
            .forEach(p -> System.out.println(p.getId() + ". " + p.getTipoDeporte().getNombre() + " (" + p.getJugadores().size() + "/" + p.getCantidadJugadores() + ")"));
        
        System.out.print("\nID del partido: ");
        int idPartido = leerOpcion();

        // 2. Pedir usuarios al servicio
        List<Cuenta> usuarios = cuentaService.obtenerTodasLasCuentas();
        if (usuarios.isEmpty()) { System.out.println("⚠ No hay usuarios."); return; }
        
        System.out.println("Usuarios disponibles:");
        usuarios.forEach(u -> System.out.println(u.getId() + ". " + u.getNombre()));
        System.out.print("\nID del usuario que se une: ");
        int idUsuario = leerOpcion();

        // 3. Llamar al servicio
        try {
            boolean exito = partidoService.unirseAPartido(idPartido, idUsuario);
            if (exito) {
                System.out.println("\n✓ Usuario unido al partido!");
                // Opcional: mostrar el nuevo estado
                Partido pActualizado = partidoService.buscarPorId(idPartido).get();
                System.out.println("Estado actual del partido: " + pActualizado.getEstado().getNombre());
            }
        } catch (Exception e) {
            System.out.println("⚠ Error al unirse: " + e.getMessage());
        }
    }

    private static void gestionarEstadoPartido() {
        System.out.println("\n═══ GESTIONAR ESTADO DE PARTIDO ═══");
        
        List<Partido> partidos = partidoService.obtenerTodosLosPartidos();
        if (partidos.isEmpty()) { System.out.println("⚠ No hay partidos."); return; }

        System.out.println("Partidos disponibles:");
        partidos.forEach(p -> System.out.println(p.getId() + ". " + p.getTipoDeporte().getNombre() + " [Estado: " + p.getEstado().getNombre() + "]"));
        
        System.out.print("\nID del partido a gestionar: ");
        int idPartido = leerOpcion();
        
        // --- AHORA (Patrón State correcto) ---
        // El controlador no "setea" un estado, envía una "acción".
        System.out.println("\nAcciones disponibles:");
        System.out.println("1. Confirmar Partido (Pasa de Armado -> Confirmado)");
        System.out.println("2. Iniciar Juego (Pasa de Confirmado -> En Juego)");
        System.out.println("3. Finalizar Partido (Pasa de En Juego -> Finalizado)");
        System.out.println("4. Cancelar Partido (Pasa de Cualquier Estado -> Cancelado)");
        System.out.print("Seleccione acción: ");
        int opAccion = leerOpcion();

        try {
            switch (opAccion) {
                case 1: partidoService.confirmarPartido(idPartido); break;
                case 2: partidoService.iniciarPartido(idPartido); break;
                case 3: partidoService.finalizarPartido(idPartido); break;
                case 4: partidoService.cancelarPartido(idPartido); break;
                default: System.out.println("⚠ Opción inválida"); return;
            }
            System.out.println("\n✓ Acción aplicada.");
            Partido pActualizado = partidoService.buscarPorId(idPartido).get();
            System.out.println("Nuevo estado: " + pActualizado.getEstado().getNombre());
        } catch (Exception e) {
            System.out.println("⚠ Error al cambiar estado: " + e.getMessage());
        }
    }

    private static void buscarPartidosConEstrategia() {
        System.out.println("\n═══ BUSCAR PARTIDOS (Patrón Strategy) ═══");

        List<Cuenta> usuarios = cuentaService.obtenerTodasLasCuentas();
        if (usuarios.isEmpty()) { System.out.println("⚠ No hay usuarios."); return; }
        
        System.out.println("Seleccione quién busca:");
        usuarios.forEach(u -> System.out.println(u.getId() + ". " + u.getNombre() + " (Nivel: " + u.getNivel().getNombre() + ")"));
        System.out.print("\nID del usuario buscador: ");
        int idBuscador = leerOpcion();
        
        Optional<Cuenta> buscadorOpt = cuentaService.buscarPorId(idBuscador);
        if (buscadorOpt.isEmpty()) { System.out.println("⚠ Usuario no encontrado"); return; }
        Cuenta buscador = buscadorOpt.get();

        System.out.println("\nEstrategias de emparejamiento (RF5.d):");
        System.out.println("1. Por Nivel (Busca partidos que coincidan con tu nivel)");
        System.out.println("2. Por Cercanía (No implementado)");
        System.out.println("3. Por Historial (No implementado)");
        System.out.print("Seleccione estrategia: ");
        int opEstrategia = leerOpcion();

        IStrategyEmparejamiento estrategia;
        switch (opEstrategia) {
            case 1: 
                estrategia = new EmparejamientoPorNivel(); 
                break;
            // case 2: estrategia = new EmparejamientoCercaniaAdapter(...); break;
            // case 3: estrategia = new EmparejamientoPorHistorial(); break;
            default:
                System.out.println("⚠ Opción inválida, usando Nivel por defecto.");
                estrategia = new EmparejamientoPorNivel();
        }

        // --- AHORA ---
        // 1. Seteamos la estrategia en el servicio
        partidoService.setEstrategiaBusqueda(estrategia);
        
        // 2. El servicio usa la estrategia para filtrar
        List<Partido> partidosEncontrados = partidoService.buscarPartidos(buscador);

        System.out.println("\n✓ Partidos encontrados (" + partidosEncontrados.size() + "):");
        if (partidosEncontrados.isEmpty()) {
            System.out.println("No se encontraron partidos que coincidan con la estrategia.");
        } else {
            partidosEncontrados.forEach(p -> System.out.println(p));
        }
    }

    private static void enviarNotificacion() {
        System.out.println("\n═══ FORZAR NOTIFICACIÓN (Patrón Observer) ═══");
        
        List<Partido> partidos = partidoService.obtenerTodosLosPartidos();
        if (partidos.isEmpty()) { System.out.println("⚠ No hay partidos."); return; }

        System.out.println("Partidos disponibles:");
        partidos.forEach(p -> System.out.println(p.getId() + ". " + p.getTipoDeporte().getNombre() + " (" + p.getJugadores().size() + " jugadores)"));
        
        System.out.print("\nID del partido a notificar: ");
        int idPartido = leerOpcion();

        System.out.println("\n¡Forzando notificación a todos los participantes!");
        try {
            partidoService.notificarObservadores(idPartido);
            System.out.println("\n✓ Notificación enviada (Revisa la consola para ver los logs de envío)");
        } catch (Exception e) {
            System.out.println("⚠ Error al notificar: " + e.getMessage());
        }
    }

    private static void probarEnvioEmail() {
        System.out.println("\n═══ 🔥 PRUEBA DE ENVÍO DE EMAIL REAL (Patrón Adapter) ═══");
        System.out.print("Email destinatario de prueba: ");
        String emailDest = scanner.nextLine();

        // --- AHORA (Usando servicios) ---
        // 1. Creamos un usuario de prueba
        Cuenta cuentaPrueba = cuentaService.registrarCuenta("Usuario de Prueba", emailDest, "123", 0);
        
        // 2. Creamos un partido de prueba
        Deporte deportePrueba = deporteService.obtenerTodos().get(0); // Tomar el primer deporte
        Partido partidoPrueba = partidoService.crearPartido(
            cuentaPrueba.getId(), 
            deportePrueba, 
            10, 
            LocalDateTime.now().plusDays(1),
            new Principiante(2, cuentaPrueba)
        );

        // 3. Nos unimos al partido
        partidoService.unirseAPartido(partidoPrueba.getId(), cuentaPrueba.getId());
        
        // 4. Forzamos la notificación
        System.out.println("\n📧 Enviando email de prueba...");
        partidoService.notificarObservadores(partidoPrueba.getId());
        
        System.out.println("\n✓ Si no hubo errores, revisa tu bandeja de entrada en: " + emailDest);
    }

    private static void mostrarPartidos() {
        System.out.println("\n═══ LISTA DE PARTIDOS ═══");
        List<Partido> partidos = partidoService.obtenerTodosLosPartidos();
        if (partidos.isEmpty()) {
            System.out.println("No hay partidos registrados");
            return;
        }
        for (Partido p : partidos) {
            System.out.println("--- Partido ID: " + p.getId() + " ---");
            System.out.println(p); // (Asumiendo que Partido.toString() está bien)
            System.out.println("Estado: " + p.getEstado().getNombre());
            System.out.println("Jugadores:");
            p.getJugadores().forEach(j -> System.out.println("  - " + j.getNombre()));
            System.out.println("--------------------");
        }
    }

    private static void mostrarUsuarios() {
        System.out.println("\n═══ LISTA DE USUARIOS ═══");
        List<Cuenta> cuentas = cuentaService.obtenerTodasLasCuentas();
        if (cuentas.isEmpty()) {
            System.out.println("No hay usuarios registrados");
            return;
        }
        for (Cuenta c : cuentas) {
            System.out.println("--- Usuario ID: " + c.getId() + " ---");
            System.out.println("  Nombre: " + c.getNombre());
            System.out.println("  Email: " + c.getEmail());
            System.out.println("  Nivel: " + c.getNivel().getNombre() + " (Puntos: " + c.getNivel().getNivel() + ")");
            System.out.println("--------------------");
        }
    }

    private static void cargarDatosEjemplo() {
        System.out.println("Cargando datos de ejemplo en los repositorios...\n");

        // --- AHORA ---
        // Los datos se cargan usando los servicios
        try {
            deporteService.guardar(new Deporte(1, "Fútbol"));
            deporteService.guardar(new Deporte(2, "Básquet"));
            deporteService.guardar(new Deporte(3, "Tenis"));

            cuentaService.registrarCuenta("Juan Pérez", "juan@test.com", "pass123", 15); // Intermedio
            cuentaService.registrarCuenta("María García", "maria@test.com", "pass456", 25); // Avanzado
            cuentaService.registrarCuenta("Luis Rodríguez", "luis@test.com", "pass789", 5);  // Principiante

            System.out.println("✓ " + deporteService.obtenerTodos().size() + " deportes cargados");
            System.out.println("✓ " + cuentaService.obtenerTodasLasCuentas().size() + " usuarios cargados");
            System.out.println("✓ Configuración de email cargada (desde ConfigLoader)");
            System.out.println("Puede operar desde el menú\n");

        } catch (Exception e) {
            System.out.println("⚠ Error cargando datos de ejemplo: " + e.getMessage());
        }
        
       /*Test Geolocalizacion*/
   
       /*try {
            String address = "7167";
            double[] coords = Geolocation.geocodeAddress(address);
            System.out.printf("📍 Address: %s%nLat: %.6f, Lon: %.6f%n%n", address, coords[0], coords[1]);
             List<String> ubicaciones = Localizador.findSportsFields(coords[0], coords[1],2000);
            
                   for (String ubicacione : ubicaciones) {
                System.out.println(ubicacione);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }*/

         /*Test Geolocalizacion*/

         /*Test Mail */
         /*
        String emailRemitente = "alvalenteuade@gmail.com";
        String password = "iijf blbr zrdk zgjp";
        EmailService emailService = new EmailService(emailRemitente, password);
        emailService.enviarCorreo("alejandromvalente@gmail.com", "test", "test");
 */
         /*Test Mail */
/*
         System.out.println("--- Iniciando prueba de notificación por email ---");

        //observador
        EmailNotificacion notificadorEmail = new EmailNotificacion();

        //cuenta prueba
        Cuenta usuarioPrueba = new Cuenta(1, "Sofi Tester", "cazonleonel@gmail.com", "123");
        
        //partido prueba
        Partido partido = new Partido();
        partido.setId(2);

        //suscribir al partido
        partido.agregar(notificadorEmail);
        
        // 4b. El usuario se une como participante al partido
        partido.agregarJugador(usuarioPrueba);

        System.out.println("\n--- Configuración Lista ---");
        System.out.println("Partido: " + partido.getId());
        System.out.println("Participantes: " + partido.getParticipantes().size());
        System.out.println("Email del participante: " + usuarioPrueba.getEmail());
        System.out.println("---------------------------\n");
        

        // 5. SIMULAR EL EVENTO
        // Esto simula que el partido cambió de estado (ej. "Partido Armado")
        // y dispara el método notificarObservadores().
        System.out.println("... Simulando cambio de estado del partido ...");
        System.out.println("... Llamando a partido.notificarObservadores() ...");
        
        partido.notificarObservadores(); // <-- ¡Aquí ocurre la magia!

        System.out.println("\n--- Prueba finalizada ---");
        System.out.println("Revisa tu bandeja de entrada en: " + usuarioPrueba.getEmail());

         */
        /*EmparejamientoPorNivel emparejamientoPorNivel = new EmparejamientoPorNivel();
        emparejamientoPorNivel.Emparejar(null);*/

        
        
    }
        
    
}