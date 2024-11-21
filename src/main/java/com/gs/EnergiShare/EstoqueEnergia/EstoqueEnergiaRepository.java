package com.gs.EnergiShare.EstoqueEnergia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstoqueEnergiaRepository extends JpaRepository<EstoqueEnergia, Integer> {
    Page<EstoqueEnergia> findByDispositivoIdContaining(String dispositivoId, Pageable pageable);
}
