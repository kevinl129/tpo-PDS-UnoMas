package com.tpopdsunomas.model.observer;

import java.util.List;

import com.tpopdsunomas.model.Partido;
import com.tpopdsunomas.model.Cuenta;
import com.tpopdsunomas.model.adapter.IMail;
import com.tpopdsunomas.model.adapter.JavaMailSender;

public class EmailNotificacion implements IObserverNotificacion{

    private IMail mailSender;

    public EmailNotificacion() {

        //String user = System.getenv("EMAIL_USER");
        //String pass = System.getenv("EMAIL_PASS");
        String user = "alvalenteuade@gmail.com";
        String pass = "iijf blbr zrdk zgjp";

        this.mailSender = new JavaMailSender(user, pass);
    }

    @Override
    public void actualizar(Partido partido) {
        List<Cuenta> jugadoresAnotificar = partido.getParticipantes();

        String asunto = "¡Partido " + partido.getId() + " listo!";
        String mensaje = "¡El partido " + partido.getId() + " ha cambiado de estado!";
        
        for (Cuenta jugador : jugadoresAnotificar) {
            System.out.println("Enviando email a: " + jugador.getNombre());
            mailSender.enviarMail(jugador.getEmail(), asunto, mensaje);
        }
    }
    
}
