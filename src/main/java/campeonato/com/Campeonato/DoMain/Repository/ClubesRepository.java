package campeonato.com.Campeonato.DoMain.Repository;

import campeonato.com.Campeonato.DoMain.Entities.Clubes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClubesRepository extends JpaRepository<Clubes, Long> {
    Optional<Clubes> findByNomeAndUfIgnoreCase(String nome, String uf);
}