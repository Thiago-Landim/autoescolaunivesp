/*package com.sci.com.components;

import java.time.LocalDate;
import java.util.List;

import com.sci.com.entities.InstrutoresEntity;
import com.sci.com.repositories.InstrutorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class GerenciadorCertificados {


    private static final Logger logger = LoggerFactory.getLogger(GerenciadorCertificados.class);
    private static final int VALIDADE_CERTIFICADO_ANOS = 1;

    @Autowired
    private InstrutorRepository repository;

    @Autowired
    private JavaMailSender javaMailSender;

    public void verificarCertificados(List<InstrutoresEntity> instrutores) {
        LocalDate hoje = LocalDate.now();
        for (InstrutoresEntity instrutor : instrutores) {
            LocalDate dataCertificado = instrutor.getDataCertificado();
            if (dataCertificado != null && dataCertificado.plusYears(VALIDADE_CERTIFICADO_ANOS).isBefore(hoje)) {
                instrutor.setFuncionarioAtivo(false);
                enviarEmailExpiracao(instrutor);
            }
        }
        repository.saveAll(instrutores);
    }

    private void enviarEmailExpiracao(InstrutoresEntity instrutor) {
        try {
            String destinatario = instrutor.getEmail();
            String assunto = "Certificado Expirado";
            String mensagem = String.format(
                    "Prezado %s,\n\nInformamos que seu certificado expirou e seu status foi atualizado para inativo.\n\nAtenciosamente,\nAuto Escola",
                    instrutor.getNomeInstrutor()
            );

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("seu.email@gmail.com"); // Certifique-se de que esse e-mail está configurado no seu properties
            message.setTo(destinatario);
            message.setSubject(assunto);
            message.setText(mensagem);

            javaMailSender.send(message);
            logger.info("E-mail enviado para: {}", destinatario);

        } catch (Exception e) {
            logger.error("Erro ao enviar e-mail para {}: {}", instrutor.getEmail(), e.getMessage());
        }
    }
}*/