package com.tpopdsunomas.model;

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

}
