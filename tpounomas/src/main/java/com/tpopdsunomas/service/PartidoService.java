package com.tpopdsunomas.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.Deporte;
import com.tpopdsunomas.model.Partido;
import com.tpopdsunomas.patterns.observer.EmailNotificacion;
import com.tpopdsunomas.patterns.observer.FireBaseNotificacion;
import com.tpopdsunomas.patterns.repo.ICuentaRepository;
import com.tpopdsunomas.patterns.repo.IPartidoRepository;
import com.tpopdsunomas.patterns.strategy.IStrategyEmparejamiento;
import com.tpopdsunomas.patterns.strategyNivel.INivelJugador;

public class PartidoService {
    
    private IPartidoRepository partidoRepo;
    private ICuentaRepository cuentaRepo;
    private IStrategyEmparejamiento estrategiaBusqueda;

    public PartidoService(IPartidoRepository partidoRepo, ICuentaRepository cuentaRepo) {
        this.partidoRepo = partidoRepo;
        this.cuentaRepo = cuentaRepo;
    }

    public Partido crearPartido(int idOrganizador, Deporte deporte, int cantJugadores, LocalDateTime fechaHora, INivelJugador nivelRequerido) {

        Optional<Cuenta> organizadorOpt = cuentaRepo.buscarPorId(idOrganizador);
        if (organizadorOpt.isEmpty()) {
            System.out.println("Error Servicio: No se encontró el organizador");
            return null; 
        }
        Cuenta organizador = organizadorOpt.get();

        Partido partido = new Partido();
        partido.setDueno(organizador);
        partido.setTipoDeporte(deporte);
        partido.setCantidadJugadores(cantJugadores);
        partido.setFechaHora(fechaHora);
        partido.setNivelRequerido(nivelRequerido);

        partido.agregar(new EmailNotificacion());

        partidoRepo.guardar(partido);
        System.out.println("Servicio: Partido creado -> " + partido.getId());
        return partido;
    }
    
    public boolean unirseAPartido(int idPartido, int idUsuario) {
        Optional<Partido> partidoOpt = partidoRepo.buscarPorId(idPartido);
        Optional<Cuenta> cuentaOpt = cuentaRepo.buscarPorId(idUsuario);

        if (partidoOpt.isEmpty() || cuentaOpt.isEmpty()) {
            System.out.println("Error Servicio: Partido o Cuenta no encontrados");
            return false;
        }

        Partido partido = partidoOpt.get();
        Cuenta cuenta = cuentaOpt.get();

        partido.agregarJugador(cuenta); 
        
        partidoRepo.guardar(partido);
        return true;
    }

    public List<Partido> obtenerTodosLosPartidos() {
        return partidoRepo.buscarTodos();
    }

    public Optional<Partido> buscarPorId(int idPartido) {
        return partidoRepo.buscarPorId(idPartido); 
    }

    public void confirmarPartido(int idPartido) {
        Partido partido = getPartidoOError(idPartido);
        partido.confirmarPartido(); 
        partidoRepo.guardar(partido); 
    }

    public void iniciarPartido(int idPartido) {
        Partido partido = getPartidoOError(idPartido);
        partido.iniciarJuego(); 
        partidoRepo.guardar(partido);
    }

    public void finalizarPartido(int idPartido) {
        Partido partido = getPartidoOError(idPartido);
        partido.finalizarPartido(); 
        partidoRepo.guardar(partido);
    }

    public void cancelarPartido(int idPartido) {
        Partido partido = getPartidoOError(idPartido);
        partido.cancelarPartido(); 
        partidoRepo.guardar(partido);
    }

    public void setEstrategiaBusqueda(IStrategyEmparejamiento estrategia) {
        this.estrategiaBusqueda = estrategia;
    }

    public List<Partido> buscarPartidos(Cuenta buscador) {
        if (estrategiaBusqueda == null) {
            throw new IllegalStateException("Se debe seleccionar una estrategia de búsqueda primero.");
        }
        List<Partido> todosLosPartidos = partidoRepo.buscarTodos();
        
        return estrategiaBusqueda.buscar(buscador, todosLosPartidos);
    }

    public void notificarObservadores(int idPartido) {
        Partido partido = getPartidoOError(idPartido);
        partido.notificarObservadores();
    }

    private Partido getPartidoOError(int idPartido) {
        return partidoRepo.buscarPorId(idPartido)
                .orElseThrow(() -> new NoSuchElementException("Servicio: No se encontró el partido con ID: " + idPartido));
    }

    private Cuenta getCuentaOError(int idCuenta) {
        return cuentaRepo.buscarPorId(idCuenta)
                .orElseThrow(() -> new NoSuchElementException("Servicio: No se encontró la cuenta con ID: " + idCuenta));
    }
}
