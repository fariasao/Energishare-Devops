package com.gs.EnergiShare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gs.EnergiShare.Fornecedor.Fornecedor;
import com.gs.EnergiShare.Fornecedor.FornecedorController;
import com.gs.EnergiShare.Fornecedor.FornecedorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FornecedorController.class)
public class FornecedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FornecedorService fornecedorService;

    private Fornecedor fornecedor;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        fornecedor = Fornecedor.builder()
                .id(1)
                .nome("Empresa Solar")
                .email("contato@empresasolar.com")
                .senhaHash("hashedpassword123")
                .telefone("987654321")
                .endereco("Avenida B, 456")
                .dataCadastro(LocalDate.now())
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void listarFornecedores_DeveRetornarListaDeFornecedores() throws Exception {
        when(fornecedorService.listarFornecedores(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(fornecedor)));

        mockMvc.perform(MockMvcRequestBuilders.get("/fornecedores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("Empresa Solar"));
    }

    @Test
    public void getFornecedorById_DeveRetornarFornecedorPorId() throws Exception {
        when(fornecedorService.getFornecedorById(1)).thenReturn(Optional.of(fornecedor));

        mockMvc.perform(MockMvcRequestBuilders.get("/fornecedores/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Empresa Solar"));
    }

    @Test
    public void createFornecedor_DeveCriarNovoFornecedor() throws Exception {
        when(fornecedorService.createFornecedor(any(Fornecedor.class))).thenReturn(fornecedor);

        mockMvc.perform(MockMvcRequestBuilders.post("/fornecedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fornecedor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Empresa Solar"));
    }

    @Test
    public void updateFornecedor_DeveAtualizarFornecedorExistente() throws Exception {
        when(fornecedorService.updateFornecedor(eq(1), any(Fornecedor.class))).thenReturn(Optional.of(fornecedor));

        mockMvc.perform(MockMvcRequestBuilders.put("/fornecedores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fornecedor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Empresa Solar"));
    }

    @Test
    public void deleteFornecedor_DeveDeletarFornecedorExistente() throws Exception {
        when(fornecedorService.deleteFornecedor(1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/fornecedores/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
