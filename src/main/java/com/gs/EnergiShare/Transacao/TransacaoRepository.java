package com.gs.EnergiShare.Transacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {
    Page<Transacao> findByTipoTransacaoContaining(String tipoTransacao, Pageable pageable);
}
