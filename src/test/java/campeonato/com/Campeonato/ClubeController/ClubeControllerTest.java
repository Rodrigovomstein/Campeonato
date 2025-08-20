package campeonato.com.Campeonato.ClubeController;

import campeonato.com.Campeonato.controller.ClubeController;
import campeonato.com.Campeonato.dto.ClubeRequestDTO;
import campeonato.com.Campeonato.exception.ClubeExisteException;
import campeonato.com.Campeonato.exception.ClubeNaoEncontradoException;
import campeonato.com.Campeonato.model.Clube;
import campeonato.com.Campeonato.services.ClubeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClubeController.class)
public class ClubeControllerTest<T> {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClubeService clubeService;

    @Test
    void deveRetornarClubesFiltrados() throws Exception {
        Clube clube = new Clube();
        clube.setId(1L);
        clube.setNome("Flamengo");
        clube.setUf("RJ");
        clube.setDataCriacao(LocalDate.of(1895,11,15));
        clube.setStatus(true);

        PageImpl<Clube> page = new PageImpl<>(List.of(clube), PageRequest.of(0, 10), 1);

        Mockito.when(clubeService.listarClubes(eq("Flamengo"), eq("RJ"), eq(true), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/clube")
                        .param("nome", "Flamengo")
                        .param("uf", "RJ")
                        .param("status", "true")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].nome").value("Flamengo"))
                .andExpect(jsonPath("$.content[0].uf").value("RJ"))
                .andExpect(jsonPath("$.content[0].status").value(true));
    }

    @Test
    void deveBuscarClubePorId_Encontrado() throws Exception {
        Clube clube = new Clube();
        clube.setId(5L);
        clube.setNome("Vasco");
        clube.setUf("RJ");
        clube.setDataCriacao(LocalDate.of(1898, 8, 21));
        clube.setStatus(true);

        Mockito.when(clubeService.buscarClubePorId(5L)).thenReturn(clube);

        mockMvc.perform(get("/clube/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.nome").value("Vasco"))
                .andExpect(jsonPath("$.uf").value("RJ"));
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void cadastrarClube_comSucesso() throws Exception {
        ClubeRequestDTO dto = new ClubeRequestDTO();
        dto.setNome("Vasco");
        dto.setUf("RJ");
        dto.setDataCriacao(java.time.LocalDate.now());
        dto.setStatus(true);

        Mockito.when(clubeService.cadastrarClube(Mockito.any(ClubeRequestDTO.class)))
                .thenReturn("Clube Vasco cadastrado com sucesso!");

        mockMvc.perform(post("/clube")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Clube Vasco cadastrado com sucesso!")));
    }

    @Test
    void cadastrarClube_jaExiste() throws Exception {
        ClubeRequestDTO dto = new ClubeRequestDTO();
        dto.setNome("Vasco");
        dto.setUf("RJ");
        dto.setDataCriacao(java.time.LocalDate.now());
        dto.setStatus(true);

        Mockito.when(clubeService.cadastrarClube(Mockito.any(ClubeRequestDTO.class)))
                .thenThrow(new ClubeExisteException("Já existe um clube com esse nome nesse estado."));

        mockMvc.perform(post("/clube")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("Já existe um clube com esse nome nesse estado.")));
    }

    @Test
    void atualizarClube_comSucesso() throws Exception {
        ClubeRequestDTO dto = new ClubeRequestDTO();
        dto.setNome("Vasco Atualizado");
        dto.setUf("RJ");
        dto.setDataCriacao(LocalDate.of(1898, 8, 21));
        dto.setStatus(true);

        Mockito.when(clubeService.atualizarClube(eq(5L), any(ClubeRequestDTO.class)))
                .thenReturn("Clube Vasco Atualizado com sucesso!");

        mockMvc.perform(
                        put("/clube/5")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Clube Vasco Atualizado com sucesso!")));
    }

    @Test
    void inativarClube_sucesso() throws Exception {
        // Não precisa mockar nada, método void — sucesso padrão Mock do Mockito (não lança exceção)
        mockMvc.perform(delete("/clube/10"))
                .andExpect(status().isNoContent());
    }

    @Test
    void inativarClube_naoEncontrado() throws Exception {
        Mockito.doThrow(new ClubeNaoEncontradoException("Clube não encontrado."))
                .when(clubeService).inativarClube(eq(99L));

        mockMvc.perform(delete("/clube/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Clube não encontrado.")));
    }
}
