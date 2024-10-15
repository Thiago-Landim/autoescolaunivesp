package com.sci.com.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String to, String name) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Bem-vindo, " + name);
            helper.setText("Seu registro foi realizado com sucesso!", true);
            mailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
