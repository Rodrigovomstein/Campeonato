package campeonato.com.Campeonato.controller;

import campeonato.com.Campeonato.dto.EstadioRequestDto;
import campeonato.com.Campeonato.exception.ClubeExisteException;
import campeonato.com.Campeonato.model.Estadio;
import campeonato.com.Campeonato.services.EstadioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estadio")
public class EstadioController {

        @Autowired
        private EstadioService estadioService;

        @PostMapping
        public ResponseEntity<String> cadastrarEstadio(@RequestBody @Valid EstadioRequestDto estadioRequestDto) {
            try {
                String mensagem = estadioService.cadastrarEstadio(estadioRequestDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);
            } catch (ClubeExisteException ex) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
            }
        }
        @PutMapping("/{id}")
        public ResponseEntity<String> atualizarEstadio(
                @PathVariable Long id,
                @RequestBody @Valid EstadioRequestDTO estadioRequestDTO) {
            try {
                String mensagem = estadioService.atualizarEstadio(id, estadioRequestDTO);
                return ResponseEntity.ok(mensagem);
            } catch (EstadioExisteException ex) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
            } catch (EstadioNaoEncontradoException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> inativarEstadio(@PathVariable Long id) {
            try {
                estadioService.inativarClube(id);
                return ResponseEntity.noContent().build(); // 204, sem body
            } catch (EstadioNaoEncontradoException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> buscarEstadio(@PathVariable Long id) {
            try {
                Estadio estadio = estadioService.buscarEstadioPorId(id);
                return ResponseEntity.ok(estadio);
            } catch (EstadioNaoEncontradoException ex) {
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
            return estadioService.listarEstadio(nome, uf, status, pageable);
        }
    }
}
