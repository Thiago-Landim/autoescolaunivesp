package com.sci.com.service;

//import com.sci.com.components.GerenciadorCertificados;

import com.sci.com.entities.InstrutoresEntity;
import com.sci.com.repositories.InstrutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
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


            if (dataCertificado.plusYears(1).isBefore(hoje)) {
                entity.setStatusLicenca("Vencida");
                return 1;
            } else {
                entity.setStatusLicenca("Válida");
                return 0;
            }
        } else {
            entity.setStatusLicenca("Inválido");
            return -1;
        }
    }
}







