package com.tpopdsunomas.patterns.state;

import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.Partido;
public class NecesitaJugadores implements IEstadoPartido {
   
    @Override
    public void agregarJugador(Partido partido, Cuenta jugador) {
        if (partido.getJugadores().size() < partido.getCantidadJugadores()) {
            partido.getJugadores().add(jugador);
            jugador.agregarPartidoInscrito(partido);
            System.out.println("Jugador " + jugador.getNombre() + " agregado.");

            //transición automatica y fue
            if (partido.getJugadores().size() == partido.getCantidadJugadores()) {
                partido.setEstado(new Armado());
            }
        } else {
            System.out.println("El partido ya está lleno.");
        }
    }

    /*@Override
    public void confirmarAsistencia(Partido partido, Cuenta jugador) {
        System.out.println("Error: No se puede confirmar, el partido aún no está armado.");
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
        return "Necesita Jugadores";
    }

    @Override
    public void iniciarJuego(Partido partido) {
        System.out.println("Error: No esta listo para empezar.");
    }

    @Override
    public void finalizar(Partido partido) {
        System.out.println("Error: Noo puede finalizar si no ha empezado.");
    }
}
