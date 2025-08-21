package campeonato.com.Campeonato.adapters.inbound.controller;

import campeonato.com.Campeonato.adapters.inbound.dto.ClubeRequestDTO;
import campeonato.com.Campeonato.domain.entities.Clube;
import campeonato.com.Campeonato.domain.exception.ClubeExisteException;
import campeonato.com.Campeonato.domain.exception.ClubeNaoEncontradoException;
import campeonato.com.Campeonato.domain.ports.inbound.ClubeUseCasePort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clube")
public class ClubeController {

    private final ClubeUseCasePort clubeUseCasePort;

    public ClubeController(ClubeUseCasePort clubeUseCasePort) {
        this.clubeUseCasePort = clubeUseCasePort;
    }

    @PostMapping
    public ResponseEntity<String> cadastrarClube(@RequestBody @Valid ClubeRequestDTO clubeRequestDTO) {
        try {
            Clube clube = clubeUseCasePort.criarClube(
                    clubeRequestDTO.getNome(),
                    clubeRequestDTO.getUf(),
                    clubeRequestDTO.getDataCriacao()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Clube " + clube.getNome() + " cadastrado com sucesso!");
        }
        catch (ClubeExisteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarClube(
            @PathVariable Long id,
            @RequestBody @Valid ClubeRequestDTO clubeRequestDTO) {
        try {
            Clube clube = clubeUseCasePort.atualizarClube(
                    id,
                    clubeRequestDTO.getNome(),
                    clubeRequestDTO.getUf(),
                    clubeRequestDTO.getDataCriacao()
            );
            return ResponseEntity.ok("Clube " + clube.getNome() + " atualizado com sucesso!");
        }
        catch (ClubeExisteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
        catch (ClubeNaoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> inativarClube(@PathVariable Long id) {
        try {
            clubeUseCasePort.inativarClube(id);
            return ResponseEntity.ok("Clube inativado com sucesso!");
        }
        catch (ClubeNaoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clube> buscarClubePorId(@PathVariable Long id) {
        try {
            Clube clube = clubeUseCasePort.buscarClubePorId(id);
            return ResponseEntity.ok(clube);
        } catch (ClubeNaoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public Page<Clube> listarClube(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) Boolean status,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return clubeUseCasePort.listarClubes(nome, uf, status, pageable);
    }
}