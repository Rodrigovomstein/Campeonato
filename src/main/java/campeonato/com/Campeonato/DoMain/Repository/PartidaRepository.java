package campeonato.com.Campeonato.DoMain.Repository;

import campeonato.com.Campeonato.DoMain.Entities.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartidaRepository extends JpaRepository<Partida, Long>, JpaSpecificationExecutor<Partida> {

    List<Partida> findByEstadioAndUfAndDataHorario(String estadio, String uf, LocalDateTime dataHorario);
    List<Partida> findByEstadioIgnoreCaseAndUfIgnoreCase(String estadio, String uf);
}