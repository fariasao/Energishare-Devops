package com.gs.EnergiShare.Energia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnergiaRepository extends JpaRepository<Energia, Integer> {
    Page<Energia> findByTipoEnergiaContaining(String tipoEnergia, Pageable pageable);
}
