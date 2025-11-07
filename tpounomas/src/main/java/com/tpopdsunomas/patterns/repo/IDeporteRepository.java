package com.tpopdsunomas.patterns.repo;

import java.util.List;
import java.util.Optional;

import com.tpopdsunomas.model.Deporte;

public interface IDeporteRepository {

    void guardar(Deporte deporte);
    Optional<Deporte> buscarPorId(int id);
    List<Deporte> buscarTodos();
    void eliminar(int id);
}