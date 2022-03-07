package com.educandoweb.cursomc.services;

import com.educandoweb.cursomc.domain.Purchase;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Purchase obj);

    void sendEmail(SimpleMailMessage msg);
}
