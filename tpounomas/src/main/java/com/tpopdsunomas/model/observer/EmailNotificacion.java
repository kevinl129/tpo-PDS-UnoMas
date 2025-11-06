package com.tpopdsunomas.model.observer;

import java.util.List;

import com.tpopdsunomas.model.Partido;
import com.tpopdsunomas.model.Cuenta;

public class EmailNotificacion implements IObserverNotificacion{

    @Override
    public void actualizar(Partido partido) {
        List<Cuenta> jugadoresAnotificar = partido.getParticipantes();
        String mensaje = "test";

        for (Cuenta jugador : jugadoresAnotificar) {
            System.out.println("Enviando email a: " + jugador.toString());
        }
    }
    
}
