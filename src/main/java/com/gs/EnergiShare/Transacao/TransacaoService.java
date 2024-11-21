package com.gs.EnergiShare.Transacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public Page<Transacao> listarTransacoes(String tipoTransacao, Pageable pageable) {
        if (tipoTransacao != null) {
            return transacaoRepository.findByTipoTransacaoContaining(tipoTransacao, pageable);
        }
        return transacaoRepository.findAll(pageable);
    }

    public Optional<Transacao> getTransacaoById(Integer id) {
        return transacaoRepository.findById(id);
    }

    public Transacao createTransacao(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    public Optional<Transacao> updateTransacao(Integer id, Transacao transacaoDetails) {
        return transacaoRepository.findById(id).map(transacao -> {
            transacao.setTipoTransacao(transacaoDetails.getTipoTransacao());
            transacao.setQuantidade(transacaoDetails.getQuantidade());
            transacao.setValorTotal(transacaoDetails.getValorTotal());
            transacao.setCliente(transacaoDetails.getCliente());
            transacao.setEnergia(transacaoDetails.getEnergia());
            transacao.setBlockchainHash(transacaoDetails.getBlockchainHash());
            transacao.setStatusBlockchain(transacaoDetails.getStatusBlockchain());
            return transacaoRepository.save(transacao);
        });
    }

    public boolean deleteTransacao(Integer id) {
        return transacaoRepository.findById(id).map(transacao -> {
            transacaoRepository.delete(transacao);
            return true;
        }).orElse(false);
    }
}
