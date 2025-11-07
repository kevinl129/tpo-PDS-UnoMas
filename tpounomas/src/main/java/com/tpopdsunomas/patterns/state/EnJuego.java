package com.tpopdsunomas.patterns.state;

import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.Partido;

public class EnJuego implements IEstadoPartido{ 
   @Override
    public void agregarJugador(Partido partido, Cuenta jugador) {
        System.out.println("Error: El partido está en juego.");
    }

    /*@Override
    public void confirmarAsistencia(Partido partido, Cuenta jugador) {
        System.out.println("Error: El partido está en juego.");
    }*/

    @Override
    public void confirmar(Partido partido) {
        System.out.println("Error: El partido está en juego.");
    }

    @Override
    public void cancelar(Partido partido) {
        System.out.println("Error: No se puede cancelar un partido en juego. hay que finalizarlo.");    }

    @Override
    public String getNombre() {
        return "En Juego";
    }

    @Override
    public void iniciarJuego(Partido partido) {
        System.out.println("El partido ya está en juego.");
    }

    @Override
    public void finalizar(Partido partido) {
        partido.setEstado(new Finalizado());
    }

}
