package com.tpopdsunomas.patterns.strategyNivel;

import com.tpopdsunomas.model.Cuenta;

public interface INivelJugador {
    public void sumarPuntos(int puntos, Cuenta jugador);
    public void restarPuntos(int puntos, Cuenta jugador);
    public int getNivel();
    String getNombre();
     int getValor();
}
