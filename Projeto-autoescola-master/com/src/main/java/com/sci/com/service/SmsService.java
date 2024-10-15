package com.sci.com.service;

import com.sci.com.entities.InstrutoresEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SmsService {

    @Autowired
    private InstrutorService instrutorService;

    @Autowired
    private SendSms sendSms; // Chama a classe SendSms

    private Set<String> mensagensEnviadas = new HashSet<>(); // Para rastrear quais mensagens foram enviadas

    // Método que verifica os certificados expirando
    @Scheduled(cron = "0 * * * * ?") // Executa a cada minuto

    public void verificarCertificadosExpirando() {
        List<InstrutoresEntity> instrutores = instrutorService.toList();
        LocalDate hoje = LocalDate.now();

        for (InstrutoresEntity instrutor : instrutores) {
            if (instrutor.getDataCertificado() != null) {
                LocalDate dataCertificado = instrutor.getDataCertificado().plusYears(1);
                LocalDate dataAviso = dataCertificado.minusDays(5);

                // Verifica se a data de aviso é hoje
                if (hoje.isEqual(dataAviso)) {
                    // Verifica se já enviou a mensagem
                    if (!mensagensEnviadas.contains(instrutor.getTelefone())) {
                        sendSms.sendSms(instrutor.getTelefone(), instrutor.getNomeInstrutor(),
                                "Seu certificado vence em 5 dias. Por favor, renove com urgência!");
                        mensagensEnviadas.add(instrutor.getTelefone()); // Marca como enviada
                    }
                }
            }
        }
    }


    //preciso ajustar a logica ele faz o batch nao envia nebnsagem de expirado
    // 2 esta mandando mensagem com a data do certificado mais 1 ano para frente


    // Método para cadastrar um instrutor e enviar SMS
    public void cadastrarInstrutor(InstrutoresEntity instrutor) {
        instrutorService.salvarInstrutor(instrutor); // Salva o instrutor no banco de dados

        // Enviar mensagem de boas-vindas após o cadastro
        sendSms.sendSms(instrutor.getTelefone(), instrutor.getNomeInstrutor(),
                "Bem-vindo, " + instrutor.getNomeInstrutor() + "! Seu cadastro foi realizado com sucesso.");
    }
}