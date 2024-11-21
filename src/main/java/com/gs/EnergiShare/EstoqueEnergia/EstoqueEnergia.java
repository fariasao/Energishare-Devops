package com.gs.EnergiShare.EstoqueEnergia;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_app_estoqueEnergia")
public class EstoqueEnergia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "estoque_id")
    private Integer id;

    @NotNull(message = "{estoqueEnergia.energia_id.notnull}")
    @ManyToOne
    @JoinColumn(name = "energia_id", nullable = false)
    private com.gs.EnergiShare.Energia.Energia energia;

    @NotBlank(message = "{estoqueEnergia.dispositivo_id.notnull}")
    @Size(max = 100, message = "{estoqueEnergia.dispositivo_id.size}")
    @Column(name = "dispositivo_id", unique = true, nullable = false)
    private String dispositivoId;

    @NotNull(message = "{estoqueEnergia.quantidade_armazenada.notnull}")
    @Column(name = "quantidade_armazenada", nullable = false)
    private Double quantidadeArmazenada;

    @Builder.Default
    @Column(name = "data_atualizacao", updatable = false)
    private LocalDate dataAtualizacao = LocalDate.now();
}
