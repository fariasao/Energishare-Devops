package com.gs.EnergiShare.Fornecedor;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_app_fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fornecedor_id")
    private Integer id;

    @NotBlank(message = "{fornecedores.nome.notnull}")
    @Size(max = 100, message = "{fornecedores.nome.size}")
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank(message = "{fornecedores.email.notnull}")
    @Email(message = "{fornecedores.email.valid}")
    @Size(max = 100, message = "{fornecedores.email.size}")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank(message = "{fornecedores.senha_hash.notnull}")
    @Size(max = 255, message = "{fornecedores.senha_hash.size}")
    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Size(max = 20, message = "{fornecedores.telefone.size}")
    @Column(name = "telefone")
    private String telefone;

    @Size(max = 200, message = "{fornecedores.endereco.size}")
    @Column(name = "endereco")
    private String endereco;

    @Builder.Default
    @Column(name = "data_cadastro", updatable = false)
    private LocalDate dataCadastro = LocalDate.now();
}
