package campeonato.com.Campeonato.domain.ports.outbound;

import campeonato.com.Campeonato.domain.entities.Clube;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClubeRepositoryPort {

    Clube salvar(Clube clube);

    Optional<Clube> buscarPorId(Long id);

    Optional<Clube> buscarPorNomeEUf(String nome, String uf);

    Page<Clube> listar(String nome, String uf, Boolean ativo, Pageable pageable);

    boolean existePorNomeEUf(String nome, String uf);

    boolean existePorNomeEUfExcluindoId(String nome, String uf, Long id);

    void deletar(Long id);
}