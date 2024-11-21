package com.gs.EnergiShare.Energia;

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
@RequestMapping("energia")
@CacheConfig(cacheNames = "energia")
@Tag(name = "Energia", description = "Gerenciamento de energia no sistema.")
public class EnergiaController {

    @Autowired
    private EnergiaService energiaService;

    @GetMapping
    @Cacheable("energia")
    @Operation(summary = "Listar energia", description = "Retorna uma página com todas as energias cadastradas.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Energias retornadas com sucesso."),
        @ApiResponse(responseCode = "404", description = "Nenhuma energia encontrada.")
    })
    public Page<Energia> listarEnergia(
            @RequestParam(required = false) String tipoEnergia,
            @PageableDefault(sort = "tipoEnergia", direction = Direction.ASC) Pageable pageable) {
        return energiaService.listarEnergia(tipoEnergia, pageable);
    }

    @GetMapping("{id}")
    @Operation(summary = "Listar energia por ID", description = "Retorna os dados de uma energia específica.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Energia retornada com sucesso."),
        @ApiResponse(responseCode = "404", description = "Energia não encontrada.")
    })
    public ResponseEntity<Energia> getEnergiaById(@PathVariable Integer id) {
        return energiaService.getEnergiaById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cadastrar energia", description = "Cadastra uma nova energia no sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Energia cadastrada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Dados inválidos.")
    })
    public ResponseEntity<Energia> createEnergia(@Valid @RequestBody Energia energia) {
        Energia novaEnergia = energiaService.createEnergia(energia);
        return new ResponseEntity<>(novaEnergia, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualizar energia", description = "Atualiza os dados de uma energia existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Energia atualizada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Dados inválidos."),
        @ApiResponse(responseCode = "404", description = "Energia não encontrada.")
    })
    public ResponseEntity<Energia> updateEnergia(@PathVariable Integer id, @Valid @RequestBody Energia energiaDetails) {
        return energiaService.updateEnergia(id, energiaDetails)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deletar energia", description = "Remove uma energia do sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Energia removida com sucesso."),
        @ApiResponse(responseCode = "404", description = "Energia não encontrada.")
    })
    public ResponseEntity<Void> deleteEnergia(@PathVariable Integer id) {
        if (energiaService.deleteEnergia(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
