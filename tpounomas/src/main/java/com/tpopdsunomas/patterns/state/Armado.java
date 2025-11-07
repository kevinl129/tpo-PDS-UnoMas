package com.tpopdsunomas.patterns.state;
import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.Partido;

public class Armado implements IEstadoPartido{
   @Override
    public void agregarJugador(Partido partido, Cuenta jugador) {
        System.out.println("Error: El partido esta armado y compelto.");
    }

    /*@Override
    public void confirmarAsistencia(Partido partido, Cuenta jugador) {
        //partido.setEstado(new Confirmado());
    }*/

    @Override
    public void confirmar(Partido partido) {
        System.out.println("Error: No se puede confirmar un partido que aún necesita jugadores.");
    }

    @Override
    public void cancelar(Partido partido) {
        partido.setEstado(new Cancelado()); 
    }

    @Override
    public String getNombre() {
        return "Armado";
    }

    @Override
    public void iniciarJuego(Partido partido) {
        System.out.println("Error: El partido primero debe ser confirmado por los jugadores.");
    }

    @Override
    public void finalizar(Partido partido) {
        System.out.println("Error: El partido no puede finalizar si no ha empezado.");
    }
}
