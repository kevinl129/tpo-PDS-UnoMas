package com.tpopdsunomas.patterns.adapter;

import com.tpopdsunomas.model.EmailService;

public class JavaMailSender implements IMail{

    private EmailService emailService;

    public JavaMailSender(String username, String password) {
        this.emailService = new EmailService(username, password);
    }

    @Override
    public void enviarMail(String correoDestinatario, String asunto, String mensaje) {
        this.emailService.enviarCorreo(correoDestinatario, asunto, mensaje);
    }
    
}
