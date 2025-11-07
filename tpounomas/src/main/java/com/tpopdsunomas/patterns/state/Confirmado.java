package com.tpopdsunomas.patterns.state;

import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.Partido;

public class Confirmado implements IEstadoPartido{
    @Override
    public void agregarJugador(Partido partido, Cuenta jugador) {
        System.out.println("Error: El partido ya está confirmado.");
    }

    /*@Override
    public void confirmarAsistencia(Partido partido, Cuenta jugador) {
        System.out.println("El partido ya fue confirmado.");
    }*/

    @Override
    public void confirmar(Partido partido) {
        System.out.println("El partido ya fue confirmado.");
    }

    @Override
    public void cancelar(Partido partido) {
        partido.setEstado(new Cancelado()); 
    }

    @Override
    public String getNombre() {
        return "Confirmado";
    }

    @Override
    public void iniciarJuego(Partido partido) {
        partido.setEstado(new EnJuego());
    }

    @Override
    public void finalizar(Partido partido) {
        System.out.println("Error: El partido no puede finalizar si no ha empezado.");
    }
  
}
