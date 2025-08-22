package campeonato.com.Campeonato.adapters.inbound.controller;

import campeonato.com.Campeonato.adapters.inbound.dto.EstadioRequestDto;
import campeonato.com.Campeonato.domain.exception.EstadioExisteException;
import campeonato.com.Campeonato.domain.exception.EstadioNaoEncontradoException;
import campeonato.com.Campeonato.domain.entities.Estadio;
import campeonato.com.Campeonato.domain.ports.inbound.EstadioUseCasePort;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estadio")
public class EstadioController {

    private final EstadioUseCasePort estadioUseCasePort;

    public EstadioController(EstadioUseCasePort estadioUseCasePort) {
        this.estadioUseCasePort = estadioUseCasePort;
    }

    @PostMapping
    public ResponseEntity<String> cadastrarEstadio(@RequestBody @Valid EstadioRequestDto estadioRequestDto) {
        try {
            Estadio estadio = estadioUseCasePort.criarEstadio(
                    estadioRequestDto.getNome(),
                    estadioRequestDto.getCep(),
                    estadioRequestDto.getUf()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Estadio criado com sucesso! ID: " + estadio.getId());
        } catch (EstadioExisteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao validar dados: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarEstadio(
            @PathVariable Long id,
            @RequestBody @Valid EstadioRequestDto estadioRequestDto) {
        try {
            Estadio estadio = estadioUseCasePort.atualizarEstadio(
                    id,
                    estadioRequestDto.getNome(),
                    estadioRequestDto.getCep(),
                    estadioRequestDto.getUf()
            );
            return ResponseEntity.ok("Estadio atualizado com sucesso! ID: " + estadio.getId());
        } catch (EstadioExisteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (EstadioNaoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao validar dados: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> inativarEstadio(@PathVariable Long id) {
        try {
            estadioUseCasePort.inativarEstadio(id);
            return ResponseEntity.ok("Estadio inativado com sucesso!");
        } catch (EstadioNaoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estadio> buscarEstadioPorId(@PathVariable Long id) {
        try {
            Estadio estadio = estadioUseCasePort.buscarEstadioPorId(id);
            return ResponseEntity.ok(estadio);
        } catch (EstadioNaoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public Page<Estadio> listarEstadio(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) Boolean ativo,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return estadioUseCasePort.listarEstadio(nome, uf, ativo, pageable);
    }
}