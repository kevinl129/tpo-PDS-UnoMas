package com.tpopdsunomas.patterns.state;

import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.Partido;

public class Finalizado implements IEstadoPartido{
   @Override
    public void agregarJugador(Partido partido, Cuenta jugador) {
        System.out.println("El partido ha finalizado.");
    }

    /*@Override
    public void confirmarAsistencia(Partido partido, Cuenta jugador) {
        System.out.println("El partido ha finalizado.");
    }*/

    @Override
    public void confirmar(Partido partido) {
        System.out.println("El partido ha finalizado.");
    }

    @Override
    public void cancelar(Partido partido) {
        System.out.println("El partido ha finalizado.");
    }

    @Override
    public void iniciarJuego(Partido partido) {
        System.out.println("El partido ha finalizado.");
    }

    @Override
    public void finalizar(Partido partido) {
        System.out.println("El partido ya está finalizado.");
    }

    @Override
    public String getNombre() {
        return "Finalizado";
    }
}
