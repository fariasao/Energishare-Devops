package com.gs.EnergiShare.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Page<Cliente> listarClientes(String nome, Pageable pageable) {
        if (nome != null) {
            return clienteRepository.findByNomeContaining(nome, pageable);
        }
        return clienteRepository.findAll(pageable);
    }

    public Optional<Cliente> getClienteById(Integer id) {
        return clienteRepository.findById(id);
    }

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> updateCliente(Integer id, Cliente clienteDetails) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNome(clienteDetails.getNome());
            cliente.setEmail(clienteDetails.getEmail());
            cliente.setSenhaHash(clienteDetails.getSenhaHash());
            cliente.setTelefone(clienteDetails.getTelefone());
            cliente.setEndereco(clienteDetails.getEndereco());
            return clienteRepository.save(cliente);
        });
    }

    public boolean deleteCliente(Integer id) {
        return clienteRepository.findById(id).map(cliente -> {
            clienteRepository.delete(cliente);
            return true;
        }).orElse(false);
    }
}
