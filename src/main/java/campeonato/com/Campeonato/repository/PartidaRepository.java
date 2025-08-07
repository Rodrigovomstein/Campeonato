package campeonato.com.Campeonato.repository;

import campeonato.com.Campeonato.model.Partida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartidaRepository extends JpaRepository<Partida, Long>, JpaSpecificationExecutor<Partida> {

    Optional<Partida> findByEstadioIgnoreCaseAndDataHorario(String estadio, LocalDateTime dataHorario);

    Optional<Partida> findByEstadioAndUfIgnoreCase(String estadio, String uf);
}