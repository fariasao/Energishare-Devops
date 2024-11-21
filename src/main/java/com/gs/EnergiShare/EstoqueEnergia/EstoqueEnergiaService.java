package com.gs.EnergiShare.EstoqueEnergia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstoqueEnergiaService {

    @Autowired
    private EstoqueEnergiaRepository estoqueEnergiaRepository;

    public Page<EstoqueEnergia> listarEstoques(String dispositivoId, Pageable pageable) {
        if (dispositivoId != null) {
            return estoqueEnergiaRepository.findByDispositivoIdContaining(dispositivoId, pageable);
        }
        return estoqueEnergiaRepository.findAll(pageable);
    }

    public Optional<EstoqueEnergia> getEstoqueById(Integer id) {
        return estoqueEnergiaRepository.findById(id);
    }

    public EstoqueEnergia createEstoque(EstoqueEnergia estoqueEnergia) {
        return estoqueEnergiaRepository.save(estoqueEnergia);
    }

    public Optional<EstoqueEnergia> updateEstoque(Integer id, EstoqueEnergia estoqueDetails) {
        return estoqueEnergiaRepository.findById(id).map(estoque -> {
            estoque.setEnergia(estoqueDetails.getEnergia());
            estoque.setDispositivoId(estoqueDetails.getDispositivoId());
            estoque.setQuantidadeArmazenada(estoqueDetails.getQuantidadeArmazenada());
            return estoqueEnergiaRepository.save(estoque);
        });
    }

    public boolean deleteEstoque(Integer id) {
        return estoqueEnergiaRepository.findById(id).map(estoque -> {
            estoqueEnergiaRepository.delete(estoque);
            return true;
        }).orElse(false);
    }
}
