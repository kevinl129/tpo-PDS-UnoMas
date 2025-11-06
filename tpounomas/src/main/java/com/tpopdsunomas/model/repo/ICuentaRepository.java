package com.tpopdsunomas.model.repo;

import java.util.List;
import java.util.Optional;

import com.tpopdsunomas.model.Cuenta;

public interface ICuentaRepository {
    
    void guardar(Cuenta cuenta);
    //Optional<Cuenta> buscarPorId(long id);
    //Optional<Cuenta> buscarPorEmail(String email);
    List<Cuenta> buscarTodos();
    void eliminar(int id);
}
