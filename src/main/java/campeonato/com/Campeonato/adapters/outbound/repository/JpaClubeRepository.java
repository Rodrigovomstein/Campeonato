package campeonato.com.Campeonato.adapters.outbound.repository;

import campeonato.com.Campeonato.adapters.outbound.entities.JpaEntityClube;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaClubeRepository extends JpaRepository<JpaEntityClube, Long>, JpaSpecificationExecutor<JpaEntityClube> {

    Optional<JpaEntityClube> findByNomeAndUfIgnoreCase(String nome, String uf);

}