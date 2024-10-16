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

    // Método que verifica os certificados vencendo e vencidos
    @Scheduled(cron = "0 * * * * ?") // Executa a cada minuto
    public void verificarCertificados() {
        List<InstrutoresEntity> instrutores = instrutorService.toList();
        LocalDate hoje = LocalDate.now();

        for (InstrutoresEntity instrutor : instrutores) {
            if (instrutor.getDataCertificado() != null) {
                LocalDate dataCertificado = instrutor.getDataCertificado();
                LocalDate dataVencimento = dataCertificado.plusYears(1); // Certificado é válido por 1 ano
                LocalDate dataAviso = dataVencimento.minusDays(5); // Aviso 5 dias antes do vencimento

                // Adicionando mensagens de depuração
                System.out.println("Verificando certificados para: " + instrutor.getNomeInstrutor());
                System.out.println("Data do Certificado: " + dataCertificado);
                System.out.println("Data de Vencimento: " + dataVencimento);
                System.out.println("Data de Hoje: " + hoje);

                // Verifica se o certificado já está vencido
                if (hoje.isAfter(dataVencimento)) {
                    System.out.println("Status Licença após verificação: Vencida");
                    // Verifica se já enviou a mensagem
                    if (!mensagensEnviadas.contains(instrutor.getTelefone())) {
                        sendSms.sendSms(instrutor.getTelefone(), instrutor.getNomeInstrutor(),
                                "Seu certificado venceu em " + dataVencimento + ". Por favor, renove com urgência!");
                        mensagensEnviadas.add(instrutor.getTelefone()); // Marca como enviada
                        System.out.println("Mensagem enviada para " + instrutor.getTelefone());
                    }
                }
                // Verifica se a data de aviso é hoje
                else if (hoje.isEqual(dataAviso)) {
                    System.out.println("Status Licença após verificação: Vencendo em 5 dias");
                    // Verifica se já enviou a mensagem
                    if (!mensagensEnviadas.contains(instrutor.getTelefone())) {
                        sendSms.sendSms(instrutor.getTelefone(), instrutor.getNomeInstrutor(),
                                "Seu certificado vence em 5 dias, no dia " + dataVencimento + ". Por favor, renove com urgência!");
                        mensagensEnviadas.add(instrutor.getTelefone()); // Marca como enviada
                        System.out.println("Mensagem de aviso enviada para " + instrutor.getTelefone());
                    }
                }
            }
        }
    }
}