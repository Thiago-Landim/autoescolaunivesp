package com.sci.com.controller;

import com.sci.com.dto.InstrutoresDto;
import com.sci.com.entities.InstrutoresEntity;
import com.sci.com.mapper.IntrutoresMapper;
import com.sci.com.service.InstrutorService;
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
    public ResponseEntity<InstrutoresDto> salvarInstrutorcontroller(@RequestBody InstrutoresDto instrutoresDto) {
        InstrutoresEntity instrutorEntity = IntrutoresMapper.DtoToEntity(instrutoresDto);

        // Verifica se o CPF já existe
        if (instrutorService.buscarPorCPF(instrutorEntity.getCpf()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Retorna 409 se já existir
        }

        // Salva o novo instrutor
        InstrutoresEntity savedEntity = instrutorService.salvarInstrutor(instrutorEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(IntrutoresMapper.InstrutoresToDto(savedEntity));
    }

}





