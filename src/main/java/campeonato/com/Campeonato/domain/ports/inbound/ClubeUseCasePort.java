package campeonato.com.Campeonato.domain.ports.inbound;

import campeonato.com.Campeonato.domain.entities.Clube;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ClubeUseCasePort {

    Clube criarClube(String nome, String uf, LocalDate dataCriacao);

    Clube atualizarClube(Long id, String nome, String uf, LocalDate dataCriacao);

    void inativarClube(Long id);

    Clube buscarClubePorId(Long id);

    Page<Clube> listarClubes(String nome, String uf, Boolean ativo, Pageable pageable);
}