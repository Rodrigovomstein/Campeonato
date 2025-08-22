package campeonato.com.Campeonato.domain.ports.outbound;

import campeonato.com.Campeonato.domain.entities.Estadio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface EstadioRepositoryPort {
    Estadio salvar(Estadio estadio);

    Optional<Estadio> buscarPorId(Long id);

    Optional<Estadio> buscarPorNomeEUf(String nome, String uf);

    Page<Estadio> listar(String nome, String uf, Boolean ativo, Pageable pageable);

    boolean existePorNomeEUf(String nome, String uf);

    boolean existePorNomeEUfExcluindoId(String nome, String uf, Long id);
}