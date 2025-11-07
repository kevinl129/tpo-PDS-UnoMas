package com.tpopdsunomas.patterns.strategy;

import java.util.ArrayList;
import java.util.List;

import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.Partido;

public class EmparejamientoPorHistorial implements IStrategyEmparejamiento{

     public List<Partido> buscar(Cuenta buscador, List<Partido> todosLosPartidos) {
        List<Partido> partidos = new ArrayList<>();
        List<Cuenta> amigos = buscador.listarAmigos();
        List<Cuenta> participantes = new ArrayList<>();
        
        for(Partido partido : todosLosPartidos){
            participantes = partido.getParticipantes();
            boolean existe = participantes.stream().anyMatch(amigos::contains);
            if(existe){
                partidos.add(partido);
            }
        }
        
        return partidos;
    }
    
}
