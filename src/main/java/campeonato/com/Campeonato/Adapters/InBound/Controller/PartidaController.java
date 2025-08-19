package campeonato.com.Campeonato.Adapters.InBound.Controller;

import campeonato.com.Campeonato.Adapters.InBound.Dto.PartidaRequestDto;
import campeonato.com.Campeonato.DoMain.Exception.PartidaExisteException;
import campeonato.com.Campeonato.DoMain.Exception.PartidaNaoEncontradaException;
import campeonato.com.Campeonato.DoMain.Model.Partida;
import campeonato.com.Campeonato.DoMain.Repository.PartidaRepository;
import campeonato.com.Campeonato.Application.Services.KafkaProducerService;
import campeonato.com.Campeonato.Application.Services.PartidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


@SuppressWarnings("ALL")
@RestController
@RequestMapping("/partidas")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;
    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping
    public ResponseEntity<?> cadastrarPartida(@RequestBody @Valid PartidaRequestDto partidaRequestDto) {
        try {
            String mensagem = partidaService.cadastrarPartida(partidaRequestDto);
            kafkaProducerService.enviarCadastroPartida(mensagem);
            return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);
        } catch (PartidaExisteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarPartida(
            @PathVariable Long id,
            @RequestBody @Valid PartidaRequestDto partidaRequestDto) {
        try {
            String mensagem = partidaService.atualizarPartida(id, partidaRequestDto);
            return ResponseEntity.ok(mensagem);
        } catch (PartidaExisteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (PartidaNaoEncontradaException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarPartida(@PathVariable Long id) {
        try {
            partidaService.inativarPartida(id);
            return ResponseEntity.noContent().build(); // 204, sem body
        } catch (PartidaNaoEncontradaException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPartida(@PathVariable Long id) {
        try {
            Partida partida = partidaService.buscarPartidaPorId(id);
            return ResponseEntity.ok(partida);
        } catch (PartidaNaoEncontradaException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping
    public Page<Partida> listarPartidas(
            @RequestParam(required = false) String clube1Id,
            @RequestParam(required = false) String clube2Id,
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) String estadio,
            @RequestParam(required = false) LocalDate dataHorario,
            @RequestParam(required = false) Boolean status,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return partidaService.listarPartidas(clube1Id, clube2Id, estadio, uf, dataHorario, status, pageable);
    }
}
