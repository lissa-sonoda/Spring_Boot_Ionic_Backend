package com.educandoweb.cursomc.services;

import com.educandoweb.cursomc.domain.Client;
import com.educandoweb.cursomc.domain.Purchase;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Purchase obj);

    void sendEmail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(Purchase obj);

    void sendHtmlEmail(MimeMessage msg);

    void sendNewPasswordEmail(Client client, String newPass);
}
