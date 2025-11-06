package com.tpopdsunomas.patterns.state;

import com.tpopdsunomas.model.Partido;

public interface IEstadoPartido {
    void cambiarEstado(Partido partido);
    String getNombre();
}
