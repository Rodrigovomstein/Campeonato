package campeonato.com.Campeonato.ClubeController;

import campeonato.com.Campeonato.repository.PartidaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import campeonato.com.Campeonato.controller.PartidaController;
import campeonato.com.Campeonato.dto.PartidaRequestDto;
import campeonato.com.Campeonato.exception.PartidaExisteException;
import campeonato.com.Campeonato.exception.PartidaNaoEncontradaException;
import campeonato.com.Campeonato.model.Partida;
import campeonato.com.Campeonato.services.PartidaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(PartidaController.class)
class PartidaControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private PartidaRepository partidaRepository;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private PartidaService partidaService;

        private PartidaRequestDto getMockDto() {
            PartidaRequestDto dto = new PartidaRequestDto();
            dto.setEstadio("Maracanã");
            dto.setUf("RJ");
            dto.setClube1Id(1L);
            dto.setClube2Id(2L);
            dto.setDataHorario(java.time.LocalDateTime.now());
            dto.setStatus(true);
            return dto;
        }

    @Test
    void cadastrarPartida_DeveRetornarCreated() throws Exception {
        PartidaController partidaCpntroller = new PartidaController();
        Mockito.when(partidaService.cadastrarPartida(any()))
                .thenReturn("Partida cadastrada");

        mockMvc.perform(post("/partidas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getMockDto())))
                .andExpect(status().isCreated())
                .andExpect(content().string("Partida cadastrada"));
    }

    @Test
    void cadastrarPartida_DeveRetornarConflict() throws Exception {
        Mockito.when(partidaService.cadastrarPartida(any()))
                .thenThrow(new PartidaExisteException("Partida já existe"));

        mockMvc.perform(post("/partidas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getMockDto())))
                .andExpect(status().isConflict())
                .andExpect(content().string("Partida já existe"));
    }

    @Test
    void atualizarPartida_DeveRetornarNotFound() throws Exception {
        Mockito.when(partidaService.atualizarPartida(anyLong(), any()))
                .thenThrow(new PartidaNaoEncontradaException("Não encontrado"));

        mockMvc.perform(put("/partidas/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getMockDto())))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Não encontrado"));
    }

    @Test
    void inativarPartida_DeveRetornarNoContent() throws Exception {
        mockMvc.perform(delete("/partidas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void inativarPartida_DeveRetornarNotFound() throws Exception {
        Mockito.doThrow(new PartidaNaoEncontradaException("Não encontrado")).when(partidaService).inativarPartida(anyLong());

        mockMvc.perform(delete("/partidas/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPartida_DeveRetornarOk() throws Exception {
        Partida partida = new Partida();

        Mockito.when(partidaService.buscarPartidaPorId(1L))
                .thenReturn(partida);

        mockMvc.perform(get("/partidas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPartida_DeveRetornarNotFound() throws Exception {
        Mockito.when(partidaService.buscarPartidaPorId(anyLong()))
                .thenThrow(new PartidaNaoEncontradaException("Não encontrado"));

        mockMvc.perform(get("/partidas/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Não encontrado"));
    }

    @Test
    void listarPartidas_DeveRetornarOk() throws Exception {
        Page<Partida> page = new PageImpl<>(Collections.singletonList(new Partida()), PageRequest.of(0, 10), 1);

        Mockito.when(partidaService.listarPartidas(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(page);

        mockMvc.perform(get("/partidas")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

}
