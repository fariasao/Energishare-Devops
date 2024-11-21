package com.gs.EnergiShare.Fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    Page<Fornecedor> findByNomeContaining(String nome, Pageable pageable);
}
