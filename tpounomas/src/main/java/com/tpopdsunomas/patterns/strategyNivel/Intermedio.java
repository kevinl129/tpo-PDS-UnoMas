package com.tpopdsunomas.patterns.strategyNivel;

import com.tpopdsunomas.model.Cuenta;

public class Intermedio implements INivelJugador {
    private int nivel;
    private int min=11;
    private int max=20;
    
    public Intermedio(int setNivel, Cuenta jugador){
        this.nivel=setNivel;
        jugador.setNivel(this);
        if (this.nivel > max){
            new Avanzado(this.nivel,jugador);            
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


    public int getNivel(){
        return this.nivel;
    }
    @Override
    public String getNombre() {
        return "Intermedio";
    }
    @Override
    public int getValor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getValor'");
    }

}
