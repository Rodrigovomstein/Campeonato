package campeonato.com.Campeonato.domain.ports;

import campeonato.com.Campeonato.domain.entities.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartidaRepository extends JpaRepository<Partida, Long>, JpaSpecificationExecutor<Partida> {

    List<Partida> findByEstadioAndUfAndDataHorario(String estadio, String uf, LocalDateTime dataHorario);
    List<Partida> findByEstadioIgnoreCaseAndUfIgnoreCase(String estadio, String uf);
}