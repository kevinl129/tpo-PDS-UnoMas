package com.tpopdsunomas.model.observer;

import java.util.List;

import com.tpopdsunomas.model.Partido;
import com.tpopdsunomas.model.Usuario;

public class EmailNotificacion implements IObserverNotificacion{

    @Override
    public void actualizar(Partido partido) {
        List<Usuario> jugadoresAnotificar = partido.getParticipantes();
        String mensaje = "test";

        for (Usuario jugador : jugadoresAnotificar) {
            System.out.println("Enviando email a: " + jugador.toString());
        }
    }
    
}
