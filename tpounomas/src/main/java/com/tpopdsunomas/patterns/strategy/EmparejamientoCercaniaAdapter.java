package com.tpopdsunomas.patterns.strategy;

import java.util.ArrayList;
import java.util.List;

import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.Partido;
import com.tpopdsunomas.model.Ubicacion;
import com.tpopdsunomas.service.Geolocation;

public class EmparejamientoCercaniaAdapter implements IStrategyEmparejamiento {
    Double distanciaMax = 2.0;

     public List<Partido> buscar(Cuenta buscador, List<Partido> todosLosPartidos) {
        List<Partido> partidos = new ArrayList<>();
        Ubicacion ubicacionJugador = buscador.getUbicaciones().getFirst();
        Ubicacion ubicacionPartido;

        for(Partido partido : todosLosPartidos){
            ubicacionPartido = partido.getUbicacion();
            
            boolean existe = Geolocation.estanCerca(ubicacionPartido.getLatitud(),ubicacionPartido.getLongitud(),ubicacionJugador.getLatitud(),ubicacionJugador.getLongitud(),distanciaMax);
            if(existe){
                partidos.add(partido);
            }
        }
  
     return partidos;
    }
    
}
