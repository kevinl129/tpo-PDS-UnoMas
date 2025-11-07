package com.tpopdsunomas.patterns.state;

import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.Partido;

public class Cancelado implements IEstadoPartido{
    
    @Override
    public void agregarJugador(Partido partido, Cuenta jugador) {
        System.out.println("Este partido fue cancelado.");
    }

    /*@Override
    public void confirmarAsistencia(Partido partido, Cuenta jugador) {
        System.out.println("Este partido fue cancelado.");
    }*/

    @Override
    public void confirmar(Partido partido) {
        System.out.println("Este partido fue cancelado.");
    }

    @Override
    public void cancelar(Partido partido) {
        System.out.println("Este partido ya está cancelado.");
    }

    @Override
    public void iniciarJuego(Partido partido) {
        System.out.println("Este partido fue cancelado.");
    }

    @Override
    public void finalizar(Partido partido) {
        System.out.println("Este partido fue cancelado.");
    }

    @Override
    public String getNombre() {
        return "Cancelado";
    }
    
}
