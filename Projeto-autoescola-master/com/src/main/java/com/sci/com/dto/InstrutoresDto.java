package com.sci.com.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class InstrutoresDto {

    private Long idInstrutor;
    private LocalDate dataCertificado;
    private String nomeInstrutor;
    private String endereco;
    private String telefone;
    private String email;
    private boolean funcionarioAtivo;
    private String cpf;
    private String StatusLicenca;


}
