package campeonato.com.Campeonato.DoMain.Repository;

import campeonato.com.Campeonato.DoMain.Model.Estadio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EstadiosRepository extends JpaRepository<Estadio, Long>, JpaSpecificationExecutor<Estadio> {
    Optional<Estadio> findByNomeAndUfIgnoreCase(String nome, String uf);
}
