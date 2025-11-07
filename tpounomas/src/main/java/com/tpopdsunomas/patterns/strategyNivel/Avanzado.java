package com.tpopdsunomas.patterns.strategyNivel;

import com.tpopdsunomas.model.Cuenta;

public class Avanzado implements INivelJugador {
    private int nivel;
    private int min=21;
    
    public Avanzado(int setNivel, Cuenta jugador){
        this.nivel=setNivel;    
        jugador.setNivel(this);
 
    }


    public void sumarPuntos(int puntos, Cuenta jugador){
        this.nivel =  this.nivel + puntos;
    }

    
    public void restarPuntos(int puntos, Cuenta jugador){
        this.nivel =  this.nivel - puntos;
        if (this.nivel < min){
            Intermedio intermedio = new Intermedio(this.nivel,jugador);
            jugador.setNivel(intermedio);
        }
    }


    public int getNivel(){
        return this.nivel;
    }


    @Override
    public String getNombre() {
        return "Avanzado";
    }


    @Override
    public int getValor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getValor'");
    }

}
