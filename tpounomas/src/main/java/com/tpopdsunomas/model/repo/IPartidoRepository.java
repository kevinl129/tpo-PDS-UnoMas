package com.tpopdsunomas.model.repo;

import java.util.List;

import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.Partido;

public interface IPartidoRepository {
    void guardar(Partido partido);
    List<Partido> buscarTodos();
    void eliminar(int id);
}
