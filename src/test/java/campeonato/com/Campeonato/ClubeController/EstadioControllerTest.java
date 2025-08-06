package campeonato.com.Campeonato.ClubeController;

import campeonato.com.Campeonato.controller.EstadioController;
import campeonato.com.Campeonato.dto.EstadioRequestDto;
import campeonato.com.Campeonato.exception.EstadioExisteException;
import campeonato.com.Campeonato.exception.EstadioNaoEncontradoException;
import campeonato.com.Campeonato.services.EstadioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import campeonato.com.Campeonato.dto.ViaCepDto;
import campeonato.com.Campeonato.model.Estadio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import campeonato.com.Campeonato.services.EstadioViaCepClient;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EstadioController.class)
class EstadioControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstadioService estadioService;

    @MockBean
    private EstadioViaCepClient estadioViaCepClient;

    @BeforeEach
    void setup() {
        ViaCepDto enderecoMock = new ViaCepDto();
        enderecoMock.setCep("12345-678");

        Mockito.when(estadioViaCepClient.buscarEnderecoPorCep(Mockito.anyString()))
                .thenReturn(enderecoMock);
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void cadastrarEstadio_comSucesso() throws Exception {
        ViaCepDto enderecoMock = new ViaCepDto();
        enderecoMock.setCep("12345-678");
        enderecoMock.setLogradouro("Rua Exemplo");
        enderecoMock.setBairro("Bairro");
        enderecoMock.setLocalidade("Cidade");
        enderecoMock.setUf("RJ");

        Mockito.when(estadioViaCepClient.buscarEnderecoPorCep(any(String.class)))
                .thenReturn(enderecoMock);

        Mockito.when(estadioService.cadastrarEstadio(any(EstadioRequestDto.class)))
                .thenReturn("Estádio Maracanã cadastrado com sucesso!");

        EstadioRequestDto dto = new EstadioRequestDto();
        dto.setNome("Maracanã");
        dto.setUf("RJ");
        dto.setDataCriacao(LocalDate.of(1950, 6, 16));
        dto.setStatus(true);
        dto.setCep("12345-678");

        mockMvc.perform(post("/estadios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Estádio Maracanã cadastrado com sucesso!")));
    }

    @Test
    void cadastrarEstadio_jaExiste() throws Exception {

        EstadioRequestDto dto = new EstadioRequestDto();
        dto.setNome("Maracanã");
        dto.setUf("RJ");
        dto.setDataCriacao(java.time.LocalDate.of(1950, 6, 16));
        dto.setStatus(true);
        dto.setCep("12345-678");

        Mockito.when(estadioService.cadastrarEstadio(any(EstadioRequestDto.class)))
                .thenThrow(new EstadioExisteException("Já existe um estádio com esse nome nesse estado."));

        mockMvc.perform(post("/estadios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("Já existe um estádio com esse nome nesse estado.")));
    }

    @Test
    void atualizarEstadio_comSucesso() throws Exception {
        EstadioRequestDto dto = new EstadioRequestDto();
        dto.setNome("Maracanã Atualizado");
        dto.setUf("RJ");
        dto.setDataCriacao(LocalDate.of(1950, 6, 16));
        dto.setStatus(true);
        dto.setCep("12345-678");

        Mockito.when(estadioService.atualizarEstadio(eq(9L), any(EstadioRequestDto.class)))
                .thenReturn("Estádio atualizado com sucesso!");
        Mockito.when(estadioViaCepClient.buscarCep(any(String.class)))
                .thenReturn(new ViaCepDto("12345-678", "Rua Exemplo", "Bairro", "Cidade", "UF"));
        mockMvc.perform(put("/estadios/9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Estádio atualizado com sucesso!")));
    }

    @Test
    void atualizarEstadio_jaExiste() throws Exception {

        EstadioRequestDto dto = new EstadioRequestDto();
        dto.setNome("Maracanã");
        dto.setUf("RJ");
        dto.setDataCriacao(LocalDate.of(1950, 6, 16));
        dto.setStatus(true);
        dto.setCep("12345-678");

        Mockito.when(estadioService.atualizarEstadio(eq(9L), any(EstadioRequestDto.class)))
                .thenThrow(new EstadioExisteException("Já existe um estádio com esse nome nesse estado."));

        mockMvc.perform(put("/estadios/9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("Já existe um estádio com esse nome nesse estado.")));
    }

    @Test
    void atualizarEstadio_naoEncontrado() throws Exception {
        EstadioRequestDto dto = new EstadioRequestDto();
        dto.setNome("Desconhecido");
        dto.setUf("ES");
        dto.setDataCriacao(LocalDate.of(2000, 1, 1));
        dto.setStatus(false);
        dto.setCep("12345-678");

        Mockito.when(estadioService.atualizarEstadio(eq(999L), any(EstadioRequestDto.class)))
                .thenThrow(new EstadioNaoEncontradoException("Estádio não encontrado."));

        mockMvc.perform(put("/estadios/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Estádio não encontrado.")));
    }
    @Test
    void inativarEstadio_DeveRetornarNoContent_QuandoSucesso() throws Exception {
        Long id = 10L;
        Mockito.doNothing().when(estadioService).inativarEstadio(id);

        mockMvc.perform(delete("/estadios/{id}", id))
                .andExpect(status().isNoContent());
    }
    @Test
    void inativarEstadio_DeveRetornarNotFound_QuandoNaoEncontrado() throws Exception {
        Long id = 55L;
        Mockito.doThrow(new EstadioNaoEncontradoException("Estádio não encontrado"))
                .when(estadioService).inativarEstadio(id);

        mockMvc.perform(delete("/estadios/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Estádio não encontrado")));
    }
    @Test
    void buscarEstadio_DeveRetornarOk_QuandoEncontrado() throws Exception {
        Long id = 5L;
        Estadio estadio = new Estadio();
        estadio.setId(id);
        estadio.setNome("Pacaembu");
        estadio.setUf("SP");
        estadio.setStatus(true);
        estadio.setCep("12345-678");

        Mockito.when(estadioService.buscarEstadioPorId(id)).thenReturn(estadio);

        mockMvc.perform(get("/estadios/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value("Pacaembu"))
                .andExpect(jsonPath("$.uf").value("SP"))
                .andExpect(jsonPath("$.status").value(true));
    }
    @Test
    void buscarEstadio_DeveRetornarNotFound_QuandoNaoEncontrado() throws Exception {
        Long id = 99L;
        Mockito.when(estadioService.buscarEstadioPorId(id))
                .thenThrow(new EstadioNaoEncontradoException("Estádio não encontrado"));

        mockMvc.perform(get("/estadios/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Estádio não encontrado")));
    }
    @Test
    void listarEstadios_DeveRetornarListaPaginada() throws Exception {
        Estadio estadio1 = new Estadio(); estadio1.setNome("Morumbi"); estadio1.setUf("SP");
        Estadio estadio2 = new Estadio(); estadio2.setNome("Mineirão"); estadio2.setUf("MG");

        Mockito.when(estadioService.listarEstadio(any(), any(), any(), any()))
                .thenReturn(new PageImpl<>(List.of(estadio1, estadio2), PageRequest.of(0, 2), 2));

        mockMvc.perform(get("/estadios") // ajuste para seu endpoint
                        .param("nome", "")
                        .param("uf", "")
                        .param("status", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2));
    }
}
