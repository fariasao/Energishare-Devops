package com.gs.EnergiShare.Transacao;

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
@RequestMapping("transacoes")
@CacheConfig(cacheNames = "transacoes")
@Tag(name = "Transacoes", description = "Gerenciamento de transações no sistema.")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping
    @Cacheable("transacoes")
    @Operation(summary = "Listar transações", description = "Retorna uma página com todas as transações cadastradas.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Transações retornadas com sucesso."),
        @ApiResponse(responseCode = "404", description = "Nenhuma transação encontrada.")
    })
    public Page<Transacao> listarTransacoes(
            @RequestParam(required = false) String tipoTransacao,
            @PageableDefault(sort = "tipoTransacao", direction = Direction.ASC) Pageable pageable) {
        return transacaoService.listarTransacoes(tipoTransacao, pageable);
    }

    @GetMapping("{id}")
    @Operation(summary = "Listar transação por ID", description = "Retorna os dados de uma transação específica.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Transação retornada com sucesso."),
        @ApiResponse(responseCode = "404", description = "Transação não encontrada.")
    })
    public ResponseEntity<Transacao> getTransacaoById(@PathVariable Integer id) {
        return transacaoService.getTransacaoById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar transação", description = "Cadastra uma nova transação no sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Transação cadastrada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Dados inválidos.")
    })
    public ResponseEntity<Transacao> createTransacao(@Valid @RequestBody Transacao transacao) {
        Transacao novaTransacao = transacaoService.createTransacao(transacao);
        return new ResponseEntity<>(novaTransacao, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualizar transação", description = "Atualiza os dados de uma transação existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Transação atualizada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Dados inválidos."),
        @ApiResponse(responseCode = "404", description = "Transação não encontrada.")
    })
    public ResponseEntity<Transacao> updateTransacao(@PathVariable Integer id, @Valid @RequestBody Transacao transacaoDetails) {
        return transacaoService.updateTransacao(id, transacaoDetails)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deletar transação", description = "Remove uma transação do sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Transação removida com sucesso."),
        @ApiResponse(responseCode = "404", description = "Transação não encontrada.")
    })
    public ResponseEntity<Void> deleteTransacao(@PathVariable Integer id) {
        if (transacaoService.deleteTransacao(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
