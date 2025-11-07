package com.tpopdsunomas.patterns.strategy;

import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.Partido;

import java.util.*;

public interface IStrategyEmparejamiento {
    //public void Emparejar(Partido partido, List<Partido> todosLosPartidos);
    List<Partido> buscar(Cuenta buscador, List<Partido> todosLosPartidos);
}
