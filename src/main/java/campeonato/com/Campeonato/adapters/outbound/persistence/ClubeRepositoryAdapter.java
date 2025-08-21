package campeonato.com.Campeonato.adapters.outbound.persistence;

import campeonato.com.Campeonato.adapters.outbound.entities.JpaEntityClube;
import campeonato.com.Campeonato.adapters.outbound.repository.JpaClubeRepository;
import campeonato.com.Campeonato.domain.entities.Clube;
import campeonato.com.Campeonato.domain.ports.outbound.ClubeRepositoryPort;
import campeonato.com.Campeonato.domain.specifications.ClubeSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class ClubeRepositoryAdapter implements ClubeRepositoryPort {

    private final JpaClubeRepository jpaClubeRepository;

    public ClubeRepositoryAdapter(JpaClubeRepository jpaClubeRepository) {
        this.jpaClubeRepository = jpaClubeRepository;
    }

    @Override
    public Clube salvar(Clube clube) {
        JpaEntityClube jpaEntity = JpaEntityClubefromDomain(clube);
        JpaEntityClube savedEntity = jpaClubeRepository.save(jpaEntity);
        return savedEntity.toDomain();
    }

    private JpaEntityClube JpaEntityClubefromDomain(Clube clube) {

        return JpaEntityClube.fromDomain(clube);
    }

    @Override
    public Optional<Clube> buscarPorId(Long id) {
        return jpaClubeRepository.findById(id)
                .map(JpaEntityClube::toDomain);
    }

    @Override
    public Optional<Clube> buscarPorNomeEUf(String nome, String uf) {
        return jpaClubeRepository.findByNomeAndUfIgnoreCase(nome, uf)
                .map(JpaEntityClube::toDomain);
    }

    @Override
    public Page<Clube> listar(String nome, String uf, Boolean ativo, Pageable pageable) {
        Specification<JpaEntityClube> spec = ClubeSpecifications.nomeContem(nome)
                .and(ClubeSpecifications.ufIgual(uf))
                .and(ClubeSpecifications.statusIgual(ativo));

        return jpaClubeRepository.findAll(spec, pageable)
                .map(JpaEntityClube::toDomain);
    }

    @Override
    public boolean existePorNomeEUf(String nome, String uf) {
        return jpaClubeRepository.findByNomeAndUfIgnoreCase(nome, uf).isPresent();
    }

    @Override
    public boolean existePorNomeEUfExcluindoId(String nome, String uf, Long id) {
        return jpaClubeRepository.findByNomeAndUfIgnoreCase(nome, uf)
                .filter(clube -> !clube.getId().equals(id))
                .isPresent();
    }

    @Override
    public void deletar(Long id) {
        jpaClubeRepository.deleteById(id);
    }
}