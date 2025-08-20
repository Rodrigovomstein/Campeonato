package campeonato.com.Campeonato.repository;

import campeonato.com.Campeonato.model.Estadio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EstadioRepository extends JpaRepository<Estadio, Long>, JpaSpecificationExecutor<Estadio> {
    Optional<Estadio> findByNomeAndUfIgnoreCase(String nome, String uf);
}
