package com.gs.EnergiShare.Cliente;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_app_clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cliente_id")
    private Integer id;

    @NotBlank(message = "{clientes.nome.notnull}")
    @Size(max = 100, message = "{clientes.nome.size}")
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank(message = "{clientes.email.notnull}")
    @Email(message = "{clientes.email.valid}")
    @Size(max = 100, message = "{clientes.email.size}")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank(message = "{clientes.senha_hash.notnull}")
    @Size(max = 255, message = "{clientes.senha_hash.size}")
    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Size(max = 20, message = "{clientes.telefone.size}")
    @Column(name = "telefone")
    private String telefone;

    @Size(max = 200, message = "{clientes.endereco.size}")
    @Column(name = "endereco")
    private String endereco;

    @Builder.Default
    @Column(name = "data_cadastro", updatable = false)
    private LocalDate dataCadastro = LocalDate.now();
}
