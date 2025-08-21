package campeonato.com.Campeonato.adapters.inbound.controller;

import campeonato.com.Campeonato.adapters.inbound.dto.PartidaRequestDto;
import campeonato.com.Campeonato.application.usecases.PartidaUseCaseImpl;
import campeonato.com.Campeonato.domain.exception.PartidaExisteException;
import campeonato.com.Campeonato.domain.exception.PartidaNaoEncontradaException;
import campeonato.com.Campeonato.domain.entities.Partida;
import campeonato.com.Campeonato.domain.ports.outbound.KafkaPublisherPort;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    private final PartidaUseCaseImpl partidaUseCaseImpl;
    private final KafkaPublisherPort kafkaPublisherPort;

    public PartidaController(PartidaUseCaseImpl partidaUseCaseImpl, KafkaPublisherPort kafkaPublisherPort) {
        this.partidaUseCaseImpl = partidaUseCaseImpl;
        this.kafkaPublisherPort = kafkaPublisherPort;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarPartida(@RequestBody @Valid PartidaRequestDto partidaRequestDto) {
        try {
            String mensagem = partidaUseCaseImpl.cadastrarPartida(partidaRequestDto);
            kafkaPublisherPort.publicarMensagem(mensagem);
            return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);
        } catch (PartidaExisteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarPartida(
            @PathVariable Long id,
            @RequestBody @Valid PartidaRequestDto partidaRequestDto) {
        try {
            String mensagem = partidaUseCaseImpl.atualizarPartida(id, partidaRequestDto);
            return ResponseEntity.ok(mensagem);
        } catch (PartidaExisteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (PartidaNaoEncontradaException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> inativarPartida(@PathVariable Long id) {
        try {
            partidaUseCaseImpl.inativarPartida(id);
            return ResponseEntity.ok("Partida inativada com sucesso!");
        } catch (PartidaNaoEncontradaException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPartida(@PathVariable Long id) {
        try {
            Partida partida = partidaUseCaseImpl.buscarPartidaPorId(id);
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
        return partidaUseCaseImpl.listarPartidas(clube1Id, clube2Id, estadio, uf, dataHorario, status, pageable);
    }
}