package com.gs.EnergiShare.Cliente;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("clientes")
@CacheConfig(cacheNames = "clientes")
@Tag(name = "Clientes", description = "Gerenciamento de clientes no sistema.")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Cacheable("clientes")
    @Operation(summary = "Listar clientes", description = "Retorna uma página com todos os clientes cadastrados.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Clientes retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado.")
    })
    public Page<Cliente> listarClientes(
            @RequestParam(required = false) String nome,
            @PageableDefault(sort = "nome", direction = Direction.ASC) Pageable pageable) {
        return clienteService.listarClientes(nome, pageable);
    }

    @GetMapping("{id}")
    @Operation(summary = "Listar cliente por ID", description = "Retorna os dados de um cliente específico.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente retornado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        return clienteService.getClienteById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar cliente", description = "Cadastra um novo cliente no sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Dados inválidos.")
    })
    public ResponseEntity<Cliente> createCliente(@Valid @RequestBody Cliente cliente) {
        Cliente novoCliente = clienteService.createCliente(cliente);
        return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Dados inválidos."),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    public ResponseEntity<Cliente> updateCliente(@PathVariable Integer id, @Valid @RequestBody Cliente clienteDetails) {
        return clienteService.updateCliente(id, clienteDetails)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso."),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        if (clienteService.deleteCliente(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
