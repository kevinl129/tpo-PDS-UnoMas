package com.tpopdsunomas.patterns.state;

import com.tpopdsunomas.model.Partido;
public class NecesitaJugadores implements IEstadoPartido {
    @Override
    public void cambiarEstado(Partido partido) {
        if (partido.isTotalmenteAceptado()) {
           // partido.setEstado(new Armado());
            System.out.println("Estado cambiado: Armado");
        } else {
            System.out.println("Aún se necesitan más jugadores");
        }
    }
    
    public void cantidadActualJugadores(int cantidad) {
        System.out.println("Cantidad actual de jugadores: " + cantidad);
    }
    
    @Override
    public String getNombre() {
        return "Necesita Jugadores";
    }
}
