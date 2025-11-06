package com.tpopdsunomas.patterns.observer;

import java.util.List;

import com.tpopdsunomas.model.Partido;
import com.tpopdsunomas.patterns.adapter.IMail;
import com.tpopdsunomas.patterns.adapter.JavaMailSender;
import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.util.ConfigLoader;

public class EmailNotificacion implements IObserverNotificacion{

    private IMail mailSender;

    public EmailNotificacion() {

        ConfigLoader config = ConfigLoader.getInstance();

        String user = config.getProperty("EMAIL_USER");
        String pass = config.getProperty("EMAIL_PASS");

        this.mailSender = new JavaMailSender(user, pass);
    }

    @Override
    public void actualizar(Partido partido) {
        List<Cuenta> jugadoresAnotificar = partido.getParticipantes();

        String asunto = "Partido " + partido.getId() + " listo!";
        String mensaje = "El partido " + partido.getId() + " ha cambiado a estado: " /*+ partido.getEstado().getNombre()*/;
        
        for (Cuenta jugador : jugadoresAnotificar) {
            System.out.println("Enviando email a: " + jugador.getNombre());
            mailSender.enviarMail(jugador.getEmail(), asunto, mensaje);
        }
    }
    
}
