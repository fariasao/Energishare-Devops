package com.gs.EnergiShare.Energia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnergiaService {

    @Autowired
    private EnergiaRepository energiaRepository;

    public Page<Energia> listarEnergia(String tipoEnergia, Pageable pageable) {
        if (tipoEnergia != null) {
            return energiaRepository.findByTipoEnergiaContaining(tipoEnergia, pageable);
        }
        return energiaRepository.findAll(pageable);
    }

    public Optional<Energia> getEnergiaById(Integer id) {
        return energiaRepository.findById(id);
    }

    public Energia createEnergia(Energia energia) {
        return energiaRepository.save(energia);
    }

    public Optional<Energia> updateEnergia(Integer id, Energia energiaDetails) {
        return energiaRepository.findById(id).map(energia -> {
            energia.setTipoEnergia(energiaDetails.getTipoEnergia());
            energia.setQuantidadeDisponivel(energiaDetails.getQuantidadeDisponivel());
            energia.setPrecoUnitario(energiaDetails.getPrecoUnitario());
            energia.setFornecedor(energiaDetails.getFornecedor());
            return energiaRepository.save(energia);
        });
    }

    public boolean deleteEnergia(Integer id) {
        return energiaRepository.findById(id).map(energia -> {
            energiaRepository.delete(energia);
            return true;
        }).orElse(false);
    }
}
