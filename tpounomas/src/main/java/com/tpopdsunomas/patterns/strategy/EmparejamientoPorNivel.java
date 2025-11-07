package com.tpopdsunomas.patterns.strategy;

import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.Partido;
import com.tpopdsunomas.patterns.strategyNivel.Avanzado;
import com.tpopdsunomas.patterns.strategyNivel.INivelJugador;

import java.util.*;
import java.util.stream.Collectors;

public class EmparejamientoPorNivel implements IStrategyEmparejamiento{
    public void emparejar(Partido partido, List<Partido> todosLosPartidos){
        List<Cuenta> jugadores = new ArrayList<>();
        Cuenta jugador1 = new Cuenta(1, "Jhon", "Jhon@gmail.com", "Clave",0,"7167");
        Cuenta jugador2 = new Cuenta(2, "Maria", "Maria@gmail.com", "Clave",0,"7167");
        Cuenta jugador3 = new Cuenta(3, "Carlos", "Carlos@gmail.com", "Clave",0,"7167");
        Cuenta jugador4 = new Cuenta(4, "Ana", "Ana@gmail.com", "Clave",0,"7167");
        Cuenta jugador5 = new Cuenta(5, "Luis", "Luis@gmail.com", "Clave",11,"7167");
        Cuenta jugador6 = new Cuenta(6, "Sofia", "Sofia@gmail.com", "Clave",11,"7167");
        Cuenta jugador7 = new Cuenta(7, "Pedro", "Pedro@gmail.com", "Clave",11,"7167");
        Cuenta jugador8 = new Cuenta(8, "Laura", "Laura@gmail.com", "Clave",11,"7167");
        Cuenta jugador9 = new Cuenta(9, "Diego", "Diego@gmail.com", "Clave",21,"7167");
        Cuenta jugador10 = new Cuenta(10, "Valentina", "Valentina@gmail.com", "Clave",21,"7167");
        Cuenta jugador11 = new Cuenta(11, "Andres", "Andres@gmail.com", "Clave",21,"7167");
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        jugadores.add(jugador3);
        jugadores.add(jugador4);
        jugadores.add(jugador5);
        jugadores.add(jugador6);
        jugadores.add(jugador7);
        jugadores.add(jugador8);
        jugadores.add(jugador9);
        jugadores.add(jugador10);
        jugadores.add(jugador11);

INivelJugador i = jugador11.getNivel();
String t = i.getClass().getName();

        List<Cuenta> jugadoresFiltrados = jugadores.stream()
        .filter(j -> j.getNivel() instanceof Avanzado)
        .collect(Collectors.toList());

 
        jugadoresFiltrados.forEach(j -> System.out.println(j.getNombre()));

    }

    @Override
    public List<Partido> buscar(Cuenta buscador, List<Partido> todosLosPartidos) {
        
        System.out.println("Usando Estrategia: Buscando partidos para nivel: " + buscador.getNivel().getNombre());
        
        INivelJugador nivelDelBuscador = buscador.getNivel();

        return todosLosPartidos.stream()
            
            .filter(partido -> partido.getEstado().getNombre().equals("Necesita Jugadores"))
            
            .filter(partido -> {
                INivelJugador nivelRequerido = partido.getNivelRequerido();
                
                if (nivelRequerido == null) {
                    return true;
                }

                return nivelRequerido.getNombre().equals(nivelDelBuscador.getNombre());
            })
            
            .collect(Collectors.toList());
    }
}
