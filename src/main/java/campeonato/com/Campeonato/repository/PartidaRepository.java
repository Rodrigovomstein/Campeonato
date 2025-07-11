package campeonato.com.Campeonato.repository;

import campeonato.com.Campeonato.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PartidaRepository extends JpaRepository<Partida, Long> {
    Optional<Partida> findByEstadioAndUfIgnoreCase(String estadio, String uf);
}