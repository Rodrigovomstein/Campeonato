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
    Optional<Partida> findByNomeAndUfIgnoreCase(String Estadio, String uf, LocalDateTime dataHorario, Boolean status, String clube1Id, String clube2Id);

    Optional<Object> findByEstadioAndUfIgnoreCase(@NotBlank(message = "Estádio é obrigatório") @Size(min = 2, message = "O nome do estádio deve ter pelo menos 2 caracteres") String estadio, @NotBlank(message = "UF é obrigatória") @Pattern(
            regexp = "^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$",
            message = "UF inválida"
    ) String uf);
}
