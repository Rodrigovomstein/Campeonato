package campeonato.com.Campeonato.Adapters.InBound.Controller;

import campeonato.com.Campeonato.Adapters.InBound.Dto.ClubesRequestDTO;
import campeonato.com.Campeonato.DoMain.Entities.Clubes;
import campeonato.com.Campeonato.DoMain.Exception.ClubesExisteException;
import campeonato.com.Campeonato.DoMain.Exception.ClubesNaoEncontradoException;
import campeonato.com.Campeonato.DoMain.Model.Clube;
import campeonato.com.Campeonato.Application.Services.ClubesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;




@RestController
@RequestMapping("/clubes")
public class ClubesController {

    @Autowired
    private ClubesService clubeService;

    @PostMapping
    public ResponseEntity<String> cadastrarClubes(@RequestBody @Valid ClubesRequestDTO clubesRequestDTO) {
        try {
            String mensagem = clubeService.cadastrarClubes(clubesRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);
        }
        catch (ClubesExisteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarClubes(
            @PathVariable Long id,
            @RequestBody @Valid ClubesRequestDTO clubesRequestDTO) {
        try {
            String mensagem = clubesService.atualizarClubes(id, clubesRequestDTO);
            return ResponseEntity.ok(mensagem);
        }
        catch (ClubesExisteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
        catch (ClubesNaoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> inativarClubes(@PathVariable Long id) {
        try {
            clubesService.inativarClube(id);
            return ResponseEntity.noContent().build(); // 204, sem body
        }
        catch (ClubesNaoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarClubes(@PathVariable Long id) {
        try {
            Clubes clubes = clubesService.buscarClubePorId(id);
            return ResponseEntity.ok(clubes);
        }
        catch (ClubesNaoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping
    public Page<Clubes> listarClubes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) Boolean status,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return clubesService.listarClubes(nome, uf, status, pageable);
    }

}



