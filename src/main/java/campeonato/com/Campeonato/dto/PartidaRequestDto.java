package campeonato.com.Campeonato.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PartidaRequestDto {

    @NotBlank(message = "Estádio é obrigatório")
    @Size(min = 2, message = "O nome do estádio deve ter pelo menos 2 caracteres")
    private String estadio;

    @NotBlank(message = "UF é obrigatória")
    @Pattern(
            regexp = "^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$",
            message = "UF inválida"
    )
    private String uf;

    @NotNull(message = "Data e horário são obrigatórios")
    @PastOrPresent(message = "A data e horário devem estar no passado ou presente")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dataHorario;

    @NotNull(message = "O status é obrigatório")
    private Boolean status;
}