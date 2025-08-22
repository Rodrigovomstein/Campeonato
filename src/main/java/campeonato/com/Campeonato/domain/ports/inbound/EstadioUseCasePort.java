package campeonato.com.Campeonato.domain.ports.inbound;

import campeonato.com.Campeonato.domain.entities.Estadio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstadioUseCasePort {

    Estadio criarEstadio(String nome, String cep, String uf);

    Estadio atualizarEstadio(Long id, String nome, String cep, String uf);

    void inativarEstadio(Long id);

    Estadio buscarEstadioPorId(Long id);

    Page<Estadio> listarEstadio(String nome, String uf, Boolean ativo, Pageable pageable);
}