package com.sci.com.service;

import com.twilio.rest.api.v2010.account.Message;



import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class SendSms {



    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public void sendSms(String numeroTelefone, String nomeInstrutor, String mensagem) {
        // Inicializa o cliente Twilio
        com.twilio.Twilio.init(accountSid, authToken);

        // Envia a mensagem SMS
        Message.creator(
                new PhoneNumber(numeroTelefone),
                new PhoneNumber(twilioPhoneNumber),
                mensagem
        ).create();

        System.out.println("SMS enviado para " + numeroTelefone + ": " + mensagem);
    }
}
