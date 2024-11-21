package com.gs.EnergiShare.Fornecedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public Page<Fornecedor> listarFornecedores(String nome, Pageable pageable) {
        if (nome != null) {
            return fornecedorRepository.findByNomeContaining(nome, pageable);
        }
        return fornecedorRepository.findAll(pageable);
    }

    public Optional<Fornecedor> getFornecedorById(Integer id) {
        return fornecedorRepository.findById(id);
    }

    public Fornecedor createFornecedor(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public Optional<Fornecedor> updateFornecedor(Integer id, Fornecedor fornecedorDetails) {
        return fornecedorRepository.findById(id).map(fornecedor -> {
            fornecedor.setNome(fornecedorDetails.getNome());
            fornecedor.setEmail(fornecedorDetails.getEmail());
            fornecedor.setSenhaHash(fornecedorDetails.getSenhaHash());
            fornecedor.setTelefone(fornecedorDetails.getTelefone());
            fornecedor.setEndereco(fornecedorDetails.getEndereco());
            return fornecedorRepository.save(fornecedor);
        });
    }

    public boolean deleteFornecedor(Integer id) {
        return fornecedorRepository.findById(id).map(fornecedor -> {
            fornecedorRepository.delete(fornecedor);
            return true;
        }).orElse(false);
    }
}
