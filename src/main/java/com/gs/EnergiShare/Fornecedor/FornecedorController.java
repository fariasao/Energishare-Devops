package com.gs.EnergiShare.Fornecedor;

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
@RequestMapping("fornecedores")
@CacheConfig(cacheNames = "fornecedores")
@Tag(name = "Fornecedores", description = "Gerenciamento de fornecedores no sistema.")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping
    @Cacheable("fornecedores")
    @Operation(summary = "Listar fornecedores", description = "Retorna uma página com todos os fornecedores cadastrados.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Fornecedores retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Nenhum fornecedor encontrado.")
    })
    public Page<Fornecedor> listarFornecedores(
            @RequestParam(required = false) String nome,
            @PageableDefault(sort = "nome", direction = Direction.ASC) Pageable pageable) {
        return fornecedorService.listarFornecedores(nome, pageable);
    }

    @GetMapping("{id}")
    @Operation(summary = "Listar fornecedor por ID", description = "Retorna os dados de um fornecedor específico.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Fornecedor retornado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado.")
    })
    public ResponseEntity<Fornecedor> getFornecedorById(@PathVariable Integer id) {
        return fornecedorService.getFornecedorById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar fornecedor", description = "Cadastra um novo fornecedor no sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Fornecedor cadastrado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Dados inválidos.")
    })
    public ResponseEntity<Fornecedor> createFornecedor(@Valid @RequestBody Fornecedor fornecedor) {
        Fornecedor novoFornecedor = fornecedorService.createFornecedor(fornecedor);
        return new ResponseEntity<>(novoFornecedor, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualizar fornecedor", description = "Atualiza os dados de um fornecedor existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Dados inválidos."),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado.")
    })
    public ResponseEntity<Fornecedor> updateFornecedor(@PathVariable Integer id, @Valid @RequestBody Fornecedor fornecedorDetails) {
        return fornecedorService.updateFornecedor(id, fornecedorDetails)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deletar fornecedor", description = "Remove um fornecedor do sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Fornecedor removido com sucesso."),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado.")
    })
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Integer id) {
        if (fornecedorService.deleteFornecedor(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
