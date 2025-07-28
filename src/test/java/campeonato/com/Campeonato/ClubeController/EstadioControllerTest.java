package campeonato.com.Campeonato.ClubeController;

import campeonato.com.Campeonato.CampeonatoApplication;
import campeonato.com.Campeonato.controller.EstadioController;
import campeonato.com.Campeonato.dto.EstadioRequestDto;
import campeonato.com.Campeonato.exception.EstadioExisteException;
import campeonato.com.Campeonato.exception.EstadioNaoEncontradoException;
import campeonato.com.Campeonato.services.EstadioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EstadioController.class)
class EstadioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstadioService estadioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void cadastrarEstadio_comSucesso() throws Exception {
        EstadioRequestDto dto = new EstadioRequestDto();
        dto.setNome("Maracanã");
        dto.setUf("RJ");
        dto.setDataCriacao(java.time.LocalDate.of(1950, 6, 16));
        dto.setStatus(true);

        Mockito.when(estadioService.cadastrarEstadio(any(EstadioRequestDto.class)))
                .thenReturn("Estádio Maracanã cadastrado com sucesso!");

        mockMvc.perform(post("/estadio")
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

        Mockito.when(estadioService.cadastrarEstadio(any(EstadioRequestDto.class)))
                .thenThrow(new EstadioExisteException("Já existe um estádio com esse nome nesse estado."));

        mockMvc.perform(post("/estadio")
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

        Mockito.when(estadioService.atualizarEstadio(eq(9L), any(EstadioRequestDto.class)))
                .thenReturn("Estádio atualizado com sucesso!");

        mockMvc.perform(put("/estadio/9")
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

        Mockito.when(estadioService.atualizarEstadio(eq(9L), any(EstadioRequestDto.class)))
                .thenThrow(new EstadioExisteException("Já existe um estádio com esse nome nesse estado."));

        mockMvc.perform(put("/estadio/9")
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

        Mockito.when(estadioService.atualizarEstadio(eq(999L), any(EstadioRequestDto.class)))
                .thenThrow(new EstadioNaoEncontradoException("Estádio não encontrado."));

        mockMvc.perform(put("/estadio/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Estádio não encontrado.")));
    }
}
