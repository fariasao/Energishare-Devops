package com.gs.EnergiShare.EstoqueEnergia;

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
@RequestMapping("estoques")
@CacheConfig(cacheNames = "estoques")
@Tag(name = "Estoque de Energia", description = "Gerenciamento de estoques de energia no sistema.")
public class EstoqueEnergiaController {

    @Autowired
    private EstoqueEnergiaService estoqueEnergiaService;

    @GetMapping
    @Cacheable("estoques")
    @Operation(summary = "Listar estoques", description = "Retorna uma página com todos os estoques cadastrados.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estoques retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Nenhum estoque encontrado.")
    })
    public Page<EstoqueEnergia> listarEstoques(
            @RequestParam(required = false) String dispositivoId,
            @PageableDefault(sort = "dispositivoId", direction = Direction.ASC) Pageable pageable) {
        return estoqueEnergiaService.listarEstoques(dispositivoId, pageable);
    }

    @GetMapping("{id}")
    @Operation(summary = "Listar estoque por ID", description = "Retorna os dados de um estoque específico.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estoque retornado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Estoque não encontrado.")
    })
    public ResponseEntity<EstoqueEnergia> getEstoqueById(@PathVariable Integer id) {
        return estoqueEnergiaService.getEstoqueById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar estoque", description = "Cadastra um novo estoque de energia no sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Estoque cadastrado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Dados inválidos.")
    })
    public ResponseEntity<EstoqueEnergia> createEstoque(@Valid @RequestBody EstoqueEnergia estoqueEnergia) {
        EstoqueEnergia novoEstoque = estoqueEnergiaService.createEstoque(estoqueEnergia);
        return new ResponseEntity<>(novoEstoque, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualizar estoque", description = "Atualiza os dados de um estoque existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Dados inválidos."),
        @ApiResponse(responseCode = "404", description = "Estoque não encontrado.")
    })
    public ResponseEntity<EstoqueEnergia> updateEstoque(@PathVariable Integer id, @Valid @RequestBody EstoqueEnergia estoqueDetails) {
        return estoqueEnergiaService.updateEstoque(id, estoqueDetails)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deletar estoque", description = "Remove um estoque do sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Estoque removido com sucesso."),
        @ApiResponse(responseCode = "404", description = "Estoque não encontrado.")
    })
    public ResponseEntity<Void> deleteEstoque(@PathVariable Integer id) {
        if (estoqueEnergiaService.deleteEstoque(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
