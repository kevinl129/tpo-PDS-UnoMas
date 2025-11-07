package com.tpopdsunomas.patterns.repo;

import java.util.List;
import java.util.Optional;

import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.Partido;

public interface IPartidoRepository {
    void guardar(Partido partido);
    Optional<Partido> buscarPorId(int id);
    List<Partido> buscarTodos();
    void eliminar(int id);
}
