package com.gs.EnergiShare.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Page<Cliente> findByNomeContaining(String nome, Pageable pageable);

    Optional<Cliente> findByEmail(String email);
}
