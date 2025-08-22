package campeonato.com.Campeonato.adapters.outbound.persistence;

import campeonato.com.Campeonato.adapters.outbound.entities.JpaEntityEstadio;
import campeonato.com.Campeonato.adapters.outbound.repository.JpaEstadioRepository;
import campeonato.com.Campeonato.domain.entities.Estadio;
import campeonato.com.Campeonato.domain.ports.outbound.EstadioRepositoryPort;
import campeonato.com.Campeonato.domain.specifications.EstadioSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class EstadioRepositoryAdapter implements EstadioRepositoryPort {

    private final JpaEstadioRepository jpaEstadioRepository;

    public EstadioRepositoryAdapter(JpaEstadioRepository jpaEstadioRepository) {
        this.jpaEstadioRepository = jpaEstadioRepository;
    }

    @Override
    public Estadio salvar(Estadio estadio) {
        JpaEntityEstadio jpaEntity = JpaEntityEstadio.fromDomain(estadio);
        JpaEntityEstadio savedEntity = jpaEstadioRepository.save(jpaEntity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Estadio> buscarPorId(Long id) {
        return jpaEstadioRepository.findById(id)
                .map(JpaEntityEstadio::toDomain);
    }

    @Override
    public Optional<Estadio> buscarPorNomeEUf(String nome, String uf) {
        return jpaEstadioRepository.findByNomeAndUfIgnoreCase(nome, uf)
                .map(JpaEntityEstadio::toDomain);
    }

    @Override
    public Page<Estadio> listar(String nome, String uf, Boolean ativo, Pageable pageable) {
        Specification<JpaEntityEstadio> spec = EstadioSpecifications.nomeContem(nome)
                .and(EstadioSpecifications.ufIgual(uf))
                .and(EstadioSpecifications.statusIgual(ativo));

        return jpaEstadioRepository.findAll(spec, pageable)
                .map(JpaEntityEstadio::toDomain);
    }

    @Override
    public boolean existePorNomeEUf(String nome, String uf) {
        return jpaEstadioRepository.findByNomeAndUfIgnoreCase(nome, uf).isPresent();
    }

    @Override
    public boolean existePorNomeEUfExcluindoId(String nome, String uf, Long id) {
        return jpaEstadioRepository.findByNomeAndUfIgnoreCase(nome, uf)
                .filter(estadio -> !estadio.getId().equals(id))
                .isPresent();
    }
}