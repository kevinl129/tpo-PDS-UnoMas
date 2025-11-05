package com.tpopdsunomas.model;

import com.tpopdsunomas.model.observer.IObserverNotificacion;

public class Usuario implements IObserverNotificacion{

    String nombre;

    @Override
    public void actualizar(Partido partido) {
        //ACA CONECTAR CON EL ENVIO DE NOTIFICACION
        System.out.println("  -> Hola " + this.nombre + ", ¡el partido " 
                          + " ya está disponible!");
    }
    
}
