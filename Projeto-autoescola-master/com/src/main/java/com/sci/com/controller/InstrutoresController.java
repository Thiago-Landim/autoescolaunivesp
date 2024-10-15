package com.sci.com.controller;

import com.sci.com.dto.InstrutoresDto;
import com.sci.com.entities.InstrutoresEntity;
import com.sci.com.mapper.IntrutoresMapper;
import com.sci.com.service.EmailService;
import com.sci.com.service.InstrutorService;
import com.sci.com.service.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/sci")
public class InstrutoresController {

    @Autowired
    private InstrutorService instrutorService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SendSms sendSms;




    //metodo usado para fins de controle da api não será implementado
    @GetMapping("/Lista")
    public ResponseEntity<List<InstrutoresDto>> findAll() {
        List<InstrutoresEntity> todos = instrutorService.toList();
        List<InstrutoresDto> todosDTO = new ArrayList<>();
        for (InstrutoresEntity entity : todos) {
            todosDTO.add(IntrutoresMapper.InstrutoresToDto(entity));
        }
        return ResponseEntity.status(HttpStatus.OK).body(todosDTO);
    }

    @GetMapping("/buscar")
    public ResponseEntity<InstrutoresDto> buscarPorCPF(@RequestParam("cpf") String cpf) {
        InstrutoresEntity instrutoresEntity = instrutorService.buscarPorCPF(cpf);

        if (instrutoresEntity != null) {
            System.out.println("Verificando certificados para: " + instrutoresEntity.getNomeInstrutor());
            instrutorService.verificarCertificadosExpirados(instrutoresEntity);
            System.out.println("Status Licença após verificação: " + instrutoresEntity.getStatusLicenca());
            return ResponseEntity.status(HttpStatus.OK).body(IntrutoresMapper.InstrutoresToDto(instrutoresEntity));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping("/save")
    public ResponseEntity<InstrutoresDto> salvarInstrutorController(@RequestBody InstrutoresDto instrutoresDto) {
        InstrutoresEntity instrutorEntity = IntrutoresMapper.DtoToEntity(instrutoresDto);

        // Verifica se o CPF já existe
        if (instrutorService.buscarPorCPF(instrutorEntity.getCpf()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Retorna 409 se já existir
        }

        // Salva o novo instrutor
        InstrutoresEntity savedEntity = instrutorService.salvarInstrutor(instrutorEntity);

        // Envia o email de boas-vindas
        emailService.sendWelcomeEmail(savedEntity.getEmail(), savedEntity.getNomeInstrutor());

        // Formata o telefone para E.164
        String telefoneFormatado = savedEntity.getTelefone().replaceAll("[^\\d]", ""); // Remove caracteres não numéricos
        if (!telefoneFormatado.startsWith("55")) {
            telefoneFormatado = "55" + telefoneFormatado; // Adiciona código do país
        }
        telefoneFormatado = "+" + telefoneFormatado; // Formata para E.164

        // Mensagem de boas-vindas
        String mensagemBoasVindas = "Olá " + savedEntity.getNomeInstrutor() + ", bem-vindo! Seu cadastro foi realizado com sucesso.";

        // Chama o método de envio de SMS para boas-vindas
        sendSms.sendSms(telefoneFormatado, savedEntity.getNomeInstrutor(), mensagemBoasVindas);

        // Mensagem de expiração do certificado
        String dataExpiracao = "Seu certificado vence em: " + savedEntity.getDataCertificado().plusYears(1).toString(); // Ajuste conforme necessário

        // Chama o método de envio de SMS para expiração do certificado (apenas se for necessário)
        // Aqui você pode optar por adicionar essa lógica de agendamento de mensagem, se necessário
        // Fornecendo o lembrete imediato ou apenas quando a data se aproximar

        return ResponseEntity.status(HttpStatus.CREATED).body(IntrutoresMapper.InstrutoresToDto(savedEntity));
    }}






