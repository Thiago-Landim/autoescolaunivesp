package com.sci.com.service;

//import com.sci.com.components.GerenciadorCertificados;
import com.sci.com.entities.InstrutoresEntity;
import com.sci.com.repositories.InstrutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Service
public class InstrutorService {


    @Autowired
    private InstrutorRepository instrutorRepository;

    //@Autowired
    //private GerenciadorCertificados gerenciadorCertificados;


    public InstrutoresEntity buscarPorCPF(String cpf) {
        return instrutorRepository.findByCpf(cpf); // Supondo que o método findByCpf está implementado no repositório
    }

    public List<InstrutoresEntity> toList() {
        return instrutorRepository.findAll();
    }

    public InstrutoresEntity salvarInstrutor(InstrutoresEntity instrutoresEntity) {
        return instrutorRepository.save(instrutoresEntity);
    }

   // public void verificarCertificadosExpirados() {
    }

    // public void verificarCertificadosExpirados() {

    //}


