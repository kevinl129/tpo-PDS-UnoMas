package com.tpopdsunomas;

import java.util.List;

import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.EmailService;
import com.tpopdsunomas.model.Geolocation;
import com.tpopdsunomas.model.Localizador;
import com.tpopdsunomas.model.Partido;
import com.tpopdsunomas.model.observer.EmailNotificacion;

public class Main {
    public static void main(String[] args) {
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

         System.out.println("--- Iniciando prueba de notificación por email ---");

        //observador
        EmailNotificacion notificadorEmail = new EmailNotificacion();

        //cuenta prueba
        Cuenta usuarioPrueba = new Cuenta(1, "Kevin Tester", "cazonleonel@gmail.com", "123");

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
    }
        
    
}