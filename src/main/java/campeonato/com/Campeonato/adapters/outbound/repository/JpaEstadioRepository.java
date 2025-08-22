package campeonato.com.Campeonato.adapters.outbound.repository;

import campeonato.com.Campeonato.adapters.outbound.entities.JpaEntityEstadio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface JpaEstadioRepository extends JpaRepository<JpaEntityEstadio, Long>, JpaSpecificationExecutor<JpaEntityEstadio> {

    Optional<JpaEntityEstadio> findByNomeAndUfIgnoreCase(String nome, String uf);

}