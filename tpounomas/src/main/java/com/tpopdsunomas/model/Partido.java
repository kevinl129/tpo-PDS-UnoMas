package com.tpopdsunomas.model;
import com.tpopdsunomas.model.observer.*;
import java.util.ArrayList;
import java.util.List;

public class Partido {
    private int id;
    private int cantidadJugadores;

    private List<IObserverNotificacion> interesados = new ArrayList<>();
    private List<Cuenta> jugadores = new ArrayList<>(); 

    public void agregarJugador(Cuenta jugador) {
        if (!jugadores.contains(jugador)) {
            jugadores.add(jugador);
            jugador.agregarPartidoInscrito(this);
            System.out.println("Jugador " + jugador.getNombre() + " agregado al partido");
            
            //esta completo??
            if (isTotalmenteAceptado()) {
                //estado.cambiarEstado(this);
                notificarObservadores();
            }
        }
    }

    public boolean isTotalmenteAceptado() {
        return jugadores.size() >= cantidadJugadores;
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

    //getters y setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}
