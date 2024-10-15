package com.sci.com.service;

//import com.sci.com.components.GerenciadorCertificados;

import com.sci.com.entities.InstrutoresEntity;
import com.sci.com.repositories.InstrutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class InstrutorService {


    @Autowired
    private InstrutorRepository instrutorRepository;


    public InstrutoresEntity buscarPorCPF(String cpf) {
        return instrutorRepository.findByCpf(cpf); // Supondo que o método findByCpf está implementado no repositório
    }

    public List<InstrutoresEntity> toList() {
        return instrutorRepository.findAll();
    }

    public InstrutoresEntity salvarInstrutor(InstrutoresEntity instrutoresEntity) {
        return instrutorRepository.save(instrutoresEntity);
    }


    public int verificarCertificadosExpirados(InstrutoresEntity entity) {
        if (entity.getDataCertificado() != null) {
            LocalDate dataCertificado = entity.getDataCertificado();
            LocalDate hoje = LocalDate.now();

            // Adicione logs para verificar as datas
            System.out.println("Data do Certificado: " + dataCertificado);
            System.out.println("Data de Hoje: " + hoje);

            // Verifica se o certificado é válido (menos de 1 ano desde a data de validade)
            if (dataCertificado.plusYears(1).isAfter(hoje)) {
                entity.setStatusLicenca("Válida");
                return 0; // Válido
            } else {
                entity.setStatusLicenca("Vencida");
                return 1; // Vencido
            }
        }

        // Caso a data do certificado seja nula
        entity.setStatusLicenca("Data de Certificado não fornecida");
        return -1; // Retorna um código indicando que a data não foi fornecida
    }}













