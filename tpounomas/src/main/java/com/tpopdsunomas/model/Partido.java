package com.tpopdsunomas.model;
import com.tpopdsunomas.patterns.observer.*;
import com.tpopdsunomas.patterns.state.IEstadoPartido;
import com.tpopdsunomas.patterns.state.NecesitaJugadores;
import com.tpopdsunomas.patterns.strategyNivel.INivelJugador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Partido {
    private int id;
    private int cantidadJugadores;
    private Deporte tipoDeporte;
    //private Ubicacion ubicacion;
    private String duracion; // Por ejemplo: "90 minutos"
    private boolean cuentaConCancha;
    private Cuenta dueno;
    private IEstadoPartido estado;
    private List<IObserverNotificacion> observers;
    //private List<Estadistica> estadisticas;
    private LocalDateTime fechaHora;
    private INivelJugador nivelRequerido;
    //private IEstrategiaEmparejamiento estrategiaEmparejamiento;
    private List<IObserverNotificacion> interesados = new ArrayList<>();
    private List<Cuenta> jugadores = new ArrayList<>(); 

    public Partido(){
        this.estado = new NecesitaJugadores();
        this.jugadores = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

public Partido(int id, Deporte tipoDeporte, int cantidadJugadores, //Ubicacion ubicacion, 
                   String duracion, boolean cuentaConCancha, Cuenta dueno, LocalDateTime fechaHora,
                   INivelJugador nivelRequerido) {
        this.id = id;
        this.tipoDeporte = tipoDeporte;
        this.cantidadJugadores = cantidadJugadores;
        //this.ubicacion = ubicacion;
        this.duracion = duracion;
        this.cuentaConCancha = cuentaConCancha;
        this.dueno = dueno;
        this.fechaHora = fechaHora;
        //this.nivelRequerido = nivelRequerido;
        this.jugadores = new ArrayList<>();
        this.observers = new ArrayList<>();
        //this.estadisticas = new ArrayList<>();
        this.estado = new NecesitaJugadores();
        
        // El dueño se agrega automáticamente como jugador
        this.jugadores.add(dueno);
    }
    

    public void agregar(IObserverNotificacion observador) {
        System.out.println("(Agregando observador: " + observador + ")");
        interesados.add(observador);
    }

    public void eliminar(IObserverNotificacion observador) {
        System.out.println("(Eliminando observador: " + observador + ")");
        interesados.remove(observador);
    }

    public void notificarObservadores() {
        for (IObserverNotificacion obs : interesados) {
            obs.actualizar(this);// aca esta el truco para notificar a los participantes del partido
       }
        
    }
    public List<Cuenta> getParticipantes() {
        return this.jugadores;
    }

   /*public void inscribirJugador(Cuenta jugador) {
        if (estrategiaEmparejamiento != null && !estrategiaEmparejamiento.esCompatible(jugador, this)) {
            System.out.println("El jugador no cumple con los requisitos del partido");
            return;
        }
        
        if (jugadores.size() >= cantidadJugadores) {
            System.out.println("El partido ya está completo");
            return;
        }
        
        agregarJugador(jugador);
    }*/

    public void confirmarPartido() {
    this.estado.confirmar(this);
    }

    public void iniciarJuego() {
        this.estado.iniciarJuego(this);
    }

    public void finalizarPartido() {
        this.estado.finalizar(this);
    }
    
    public void agregarJugador(Cuenta jugador) {
        this.estado.agregarJugador(this, jugador);
    }
    
    public void confirmar() {
        this.estado.confirmar(this);
    }
    
    public void cancelarPartido() {
        this.estado.cancelar(this);
    }
    
    public boolean isTotalmenteAceptado() {
        return jugadores.size() >= cantidadJugadores;
    }
    
    public void agregarObservador(IObserverNotificacion observer) {
        observers.add(observer);
    }
    
    public void routaObservadores(IObserverNotificacion observer) {
        observers.remove(observer);
    }
    
    
   // public void agregarEstadistica(Estadistica estadistica) {
     //   estadisticas.add(estadistica);
    //}
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Deporte getTipoDeporte() {
        return tipoDeporte;
    }
    
    public void setTipoDeporte(Deporte tipoDeporte) {
        this.tipoDeporte = tipoDeporte;
    }
    
    public int getCantidadJugadores() {
        return cantidadJugadores;
    }
    
    public void setCantidadJugadores(int cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
    }
    /* 
    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
    */
    public String getDuracion() {
        return duracion;
    }
    
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
    
    public boolean isCuentaConCancha() {
        return cuentaConCancha;
    }
    
    public void setCuentaConCancha(boolean cuentaConCancha) {
        this.cuentaConCancha = cuentaConCancha;
    }
    
    public Cuenta getDueno() {
        return dueno;
    }
    
    public void setDueno(Cuenta dueno) {
        this.dueno = dueno;
    }
    
    public List<Cuenta> getJugadores() {
        return jugadores;
    }
    
    public IEstadoPartido getEstado() {
        return estado;
    }
    
    public void setEstado(IEstadoPartido nuevoEstado) {
        this.estado = nuevoEstado;
        System.out.println("--- Partido cambió de estado a: " + nuevoEstado.getNombre() + " ---");
        
        this.notificarObservadores(); 
    }
    
    //public List<Estadistica> getEstadisticas() {
      //  return estadisticas;
    //}
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    //public INivelJugador getNivelRequerido() {
      //  return nivelRequerido;
    //}
    /* 
    public void setNivelRequerido(INivelJugador nivelRequerido) {
        this.nivelRequerido = nivelRequerido;
    }
    
    public IEstrategiaEmparejamiento getEstrategiaEmparejamiento() {
        return estrategiaEmparejamiento;
    }
    
    public void setEstrategiaEmparejamiento(IEstrategiaEmparejamiento estrategiaEmparejamiento) {
        this.estrategiaEmparejamiento = estrategiaEmparejamiento;
    }*/
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Partido #" + id + " - " + tipoDeporte.getNombre() + 
               "\nUbicación: " + //ubicacion +
               "\nFecha: " + fechaHora.format(formatter) +
               "\nJugadores: " + jugadores.size() + "/" + cantidadJugadores +
               "\nEstado: " + estado.getNombre(); //+
             //  "\nNivel: "// + //nivelRequerido.getNombre();
    }
}
