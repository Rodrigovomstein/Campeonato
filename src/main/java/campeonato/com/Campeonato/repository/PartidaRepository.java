package campeonato.com.Campeonato.repository;

import campeonato.com.Campeonato.model.Partida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PartidaRepository extends JpaRepository<Partida, Long>, JpaSpecificationExecutor<Partida> {

    Optional<Partida> findByEstadioIgnoreCaseAndDataHorario(String estadio, LocalDateTime dataHorario);

    Optional<Object> findByEstadioAndUfIgnoreCase(@NotBlank(message = "Estádio é obrigatório") @Size(min = 2, message = "O nome do estádio deve ter pelo menos 2 caracteres") String estadio, @NotBlank(message = "UF é obrigatória") @Pattern(
            regexp = "^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$",
            message = "UF inválida"
    ) String uf);
}
