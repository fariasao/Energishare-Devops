package com.gs.EnergiShare.AuditLog;

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
@RequestMapping("audit-logs")
@CacheConfig(cacheNames = "auditLogs")
@Tag(name = "Audit Logs", description = "Gerenciamento de logs de auditoria no sistema.")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping
    @Cacheable("auditLogs")
    @Operation(summary = "Listar logs de auditoria", description = "Retorna uma página com todos os logs de auditoria cadastrados.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Logs retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Nenhum log encontrado.")
    })
    public Page<AuditLog> listarLogs(
            @RequestParam(required = false) String tableName,
            @PageableDefault(sort = "tableName", direction = Direction.ASC) Pageable pageable) {
        return auditLogService.listarLogs(tableName, pageable);
    }

    @GetMapping("{id}")
    @Operation(summary = "Listar log por ID", description = "Retorna os dados de um log específico.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Log retornado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Log não encontrado.")
    })
    public ResponseEntity<AuditLog> getLogById(@PathVariable Integer id) {
        return auditLogService.getLogById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar log", description = "Cadastra um novo log de auditoria no sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Log cadastrado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Dados inválidos.")
    })
    public ResponseEntity<AuditLog> createLog(@Valid @RequestBody AuditLog log) {
        AuditLog novoLog = auditLogService.createLog(log);
        return new ResponseEntity<>(novoLog, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualizar log", description = "Atualiza os dados de um log existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Log atualizado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Dados inválidos."),
        @ApiResponse(responseCode = "404", description = "Log não encontrado.")
    })
    public ResponseEntity<AuditLog> updateLog(@PathVariable Integer id, @Valid @RequestBody AuditLog logDetails) {
        return auditLogService.updateLog(id, logDetails)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deletar log", description = "Remove um log do sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Log removido com sucesso."),
        @ApiResponse(responseCode = "404", description = "Log não encontrado.")
    })
    public ResponseEntity<Void> deleteLog(@PathVariable Integer id) {
        if (auditLogService.deleteLog(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
