package campeonato.com.Campeonato.adapters.inbound.controller;

import campeonato.com.Campeonato.adapters.inbound.dto.EstadioRequestDto;
import campeonato.com.Campeonato.adapters.inbound.dto.ViaCepDto;
import campeonato.com.Campeonato.domain.exception.EstadioExisteException;
import campeonato.com.Campeonato.domain.exception.EstadioNaoEncontradoException;
import campeonato.com.Campeonato.domain.entities.Estadio;
import campeonato.com.Campeonato.application.usecases.EstadioUseCaseImpl;
import campeonato.com.Campeonato.domain.ports.outbound.ViaCepServicePort;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estadios")
public class EstadioController {

    @Autowired
    private EstadioUseCaseImpl estadioUseCaseImpl;

    @Autowired
    private ViaCepServicePort viaCepServicePort;

    @PostMapping
    public ResponseEntity<String> cadastrarEstadio(@RequestBody @Valid EstadioRequestDto estadioRequestDto) {
        try {
            ViaCepDto endereco = viaCepServicePort.buscarEnderecoPorCep(estadioRequestDto.getCep());
            estadioRequestDto.setCep(endereco.getCep());

            String mensagem = estadioUseCaseImpl.cadastrarEstadio(estadioRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);
        }
        catch (EstadioExisteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarEstadio(
            @PathVariable Long id,
            @RequestBody @Valid EstadioRequestDto estadioRequestDto) {
        try {
            String mensagem = estadioUseCaseImpl.atualizarEstadio(id, estadioRequestDto);
            return ResponseEntity.ok(mensagem);
        }
        catch (EstadioExisteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
        catch (EstadioNaoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> inativarEstadio(@PathVariable Long id) {
        try {
            estadioUseCaseImpl.inativarEstadio(id);
            return ResponseEntity.noContent().build();
        }
        catch (EstadioNaoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarEstadio(@PathVariable Long id) {
        try {
            Estadio estadio = estadioUseCaseImpl.buscarEstadioPorId(id);
            return ResponseEntity.ok(estadio);
        }
        catch (EstadioNaoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping
    public Page<Estadio> listarEstadio(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) Boolean status,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return estadioUseCaseImpl.listarEstadio(nome, uf, status, pageable);
    }
}