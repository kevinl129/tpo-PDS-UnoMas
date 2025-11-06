package com.tpopdsunomas.model;

public class Intermedio implements INivelJugador {
    private int nivel;
    private int min=11;
    private int max=20;
    
    public Intermedio(int setNivel, Cuenta jugador){
        this.nivel=setNivel;
        if (this.nivel > max){
            Intermedio intermedio = new Intermedio(this.nivel,jugador);
            jugador.setNivel(intermedio);
        }      

    }
    public void sumarPuntos(int puntos, Cuenta jugador){
        this.nivel =  this.nivel + puntos;
        if (this.nivel > max){
            Avanzado avanzado = new Avanzado(this.nivel,jugador);
            jugador.setNivel(avanzado);
        }
    }
    public void restarPuntos(int puntos, Cuenta jugador){
        this.nivel =  this.nivel - puntos;
        if (this.nivel < min){
            Principiante principiante = new Principiante(this.nivel,jugador);
            jugador.setNivel(principiante);
        }
    }


    public int getNivel(int setNivel){
        return this.nivel;
    }

}
