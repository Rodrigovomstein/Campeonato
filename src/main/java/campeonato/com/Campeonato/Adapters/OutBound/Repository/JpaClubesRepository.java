package campeonato.com.Campeonato.Adapters.OutBound.Repository;

import campeonato.com.Campeonato.DoMain.Entities.Clubes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaClubesRepository extends JpaRepository<Clubes, Long> {
    Optional<Clubes> findByNomeAndUfIgnoreCase(String nome, String uf);
}