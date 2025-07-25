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
    private Long clube1Id;
    private Long clube2Id;

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

    private Integer golsClube1;

    public Integer getGolsClube1() {
        return golsClube1;
    }

    public void setGolsClube1(Integer golsClube1) {
        this.golsClube1 = golsClube1;
    }

    private Integer golsClube2;

    public Integer getGolsClube2() {
        return golsClube2;
    }

    public void setGolsClube2(Integer golsClube2) {
        this.golsClube2 = golsClube2;
    }

}