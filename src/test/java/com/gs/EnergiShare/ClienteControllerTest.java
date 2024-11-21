package com.gs.EnergiShare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gs.EnergiShare.Cliente.Cliente;
import com.gs.EnergiShare.Cliente.ClienteController;
import com.gs.EnergiShare.Cliente.ClienteService;

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

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    private Cliente cliente;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        cliente = Cliente.builder()
                .id(1)
                .nome("Maria Silva")
                .email("maria.silva@example.com")
                .senhaHash("hashedpassword123") // Valor válido para senhaHash
                .telefone("123456789")
                .endereco("Rua A, 123")
                .dataCadastro(LocalDate.now())
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void listarClientes_DeveRetornarListaDeClientes() throws Exception {
        when(clienteService.listarClientes(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(cliente)));

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("Maria Silva"));
    }

    @Test
    public void getClienteById_DeveRetornarClientePorId() throws Exception {
        when(clienteService.getClienteById(1)).thenReturn(Optional.of(cliente));

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria Silva"));
    }

    @Test
    public void createCliente_DeveCriarNovoCliente() throws Exception {
        when(clienteService.createCliente(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Maria Silva"));
    }

    @Test
    public void updateCliente_DeveAtualizarClienteExistente() throws Exception {
        when(clienteService.updateCliente(eq(1), any(Cliente.class))).thenReturn(Optional.of(cliente));

        mockMvc.perform(MockMvcRequestBuilders.put("/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria Silva"));
    }

    @Test
    public void deleteCliente_DeveDeletarClienteExistente() throws Exception {
        when(clienteService.deleteCliente(1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
