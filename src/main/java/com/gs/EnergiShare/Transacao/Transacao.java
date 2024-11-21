package com.gs.EnergiShare.Transacao;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_app_transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transacao_id")
    private Integer id;

    @NotBlank(message = "{transacao.tipo_transacao.notnull}")
    @Size(max = 20, message = "{transacao.tipo_transacao.size}")
    @Column(name = "tipo_transacao", nullable = false)
    private String tipoTransacao;

    @NotNull(message = "{transacao.quantidade.notnull}")
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @DecimalMin(value = "0.0", inclusive = false, message = "{transacao.valor_total.min}")
    @Column(name = "valor_total")
    private Integer valorTotal;

    @Builder.Default
    @Column(name = "data_transacao", updatable = false)
    private LocalDate dataTransacao = LocalDate.now();

    @NotNull(message = "{transacao.cliente_id.notnull}")
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private com.gs.EnergiShare.Cliente.Cliente cliente;

    @NotNull(message = "{transacao.energia_id.notnull}")
    @ManyToOne
    @JoinColumn(name = "energia_id", nullable = false)
    private com.gs.EnergiShare.Energia.Energia energia;

    @Size(max = 255, message = "{transacao.blockchain_hash.size}")
    @Column(name = "blockchain_hash")
    private String blockchainHash;

    @NotBlank(message = "{transacao.status_blockchain.notnull}")
    @Size(max = 20, message = "{transacao.status_blockchain.size}")
    @Column(name = "status_blockchain", nullable = false, columnDefinition = "varchar(20) default 'Pendente'")
    private String statusBlockchain;
}
