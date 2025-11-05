package com.tpopdsunomas.model;
import com.tpopdsunomas.model.observer.*;
import java.util.ArrayList;
import java.util.List;

public class Partido {
    private List<IObserverNotificacion> interesados = new ArrayList<>();

    public void agregar(IObserverNotificacion observador) {
        System.out.println("(Agregando observador: " + observador + ")");
        interesados.add(observador);
    }

    public void eliminar(IObserverNotificacion observador) {
        System.out.println("(Eliminando observador: " + observador + ")");
        interesados.remove(observador);
    }

    public void notificarObservadores() {
        for (IObserverNotificacion obs : interesados) {
            obs.actualizar(this);
        }
}
}
