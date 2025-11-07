package com.tpopdsunomas.patterns.state;

import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.Partido;

public interface IEstadoPartido {
    //void cambiarEstado(Partido partido);
    void agregarJugador(Partido partido, Cuenta jugador);
    //void confirmarAsistencia(Partido partido, Cuenta jugador);
    void confirmar(Partido partido);
    void cancelar(Partido partido);
    void iniciarJuego(Partido partido);
    void finalizar(Partido partido);
    String getNombre();
}
