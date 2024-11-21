package com.gs.EnergiShare.Energia;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_app_energia")
public class Energia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "energia_id")
    private Integer id;

    @NotBlank(message = "{energia.tipo_energia.notnull}")
    @Size(max = 50, message = "{energia.tipo_energia.size}")
    @Column(name = "tipo_energia", nullable = false)
    private String tipoEnergia;

    @NotNull(message = "{energia.quantidade_disponivel.notnull}")
    @Column(name = "quantidade_disponivel", nullable = false)
    private Integer quantidadeDisponivel;

    @DecimalMin(value = "0.0", inclusive = false, message = "{energia.preco_unitario.min}")
    @Column(name = "preco_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal precoUnitario;

    @Builder.Default
    @Column(name = "data_geracao", updatable = false)
    private LocalDate dataGeracao = LocalDate.now();

    @NotNull(message = "{energia.fornecedor_id.notnull}")
    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private com.gs.EnergiShare.Fornecedor.Fornecedor fornecedor;
}
