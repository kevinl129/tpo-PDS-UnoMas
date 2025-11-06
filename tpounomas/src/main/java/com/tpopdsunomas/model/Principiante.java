package com.tpopdsunomas.model;

public class Principiante implements INivelJugador {
    private int nivel;
    private int max=10;
    
    public Principiante(int setNivel, Cuenta jugador){
        this.nivel=setNivel;      
        jugador.setNivel(this);
        if (this.nivel > max){
            new Intermedio(this.nivel,jugador);            
        }      
        
    }
    public void sumarPuntos(int puntos, Cuenta jugador){
        this.nivel =  this.nivel + puntos;        
        if (this.nivel > max){
            Intermedio intermedio = new Intermedio(this.nivel,jugador); 
            jugador.setNivel(intermedio);       
        }
    }
    public void restarPuntos(int puntos, Cuenta jugador){
        this.nivel =  this.nivel - puntos;
        if (this.nivel < 0){
            this.nivel=0;
        }
    }


    public int getNivel(){
        return this.nivel;
    }
    @Override
    public String getNombre() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNombre'");
    }
    @Override
    public int getValor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getValor'");
    }

}
