package com.sci.com.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "TB_Instrutores")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InstrutoresEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Ajustado para AUTO, se o id não for numérico
    @Column(name = "Id_Instrutor")
    private Long idInstrutor;

    @Column(name = "Nome_Instrutor", length = 255)
    private String nomeInstrutor;

    @Column(name = "Certificado_Expira_EM")
    private LocalDate dataCertificado;

    @Column(name = "Endereco", length = 255)
    private String endereco;

    @Column(name = "Telefone", length = 20)
    private String telefone;

    @Column(name = "EMAIL", length = 255)
    private String email;

    @Column(name = "Funcionario_ATIVO")
    private boolean funcionarioAtivo;
    @Column(name = "CPF",  unique = true)
    private String cpf;  // Adicione esta linha


    @Lob
    @Column(name = "foto")
    private byte[] foto; // Para armazenar a imagem como BLOB

}




