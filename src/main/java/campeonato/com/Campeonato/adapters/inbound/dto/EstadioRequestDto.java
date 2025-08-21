package campeonato.com.Campeonato.adapters.inbound.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EstadioRequestDto {
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, message = "O nome deve ter pelo menos 2 caracteres")
        private String nome;

        @NotBlank(message = "A UF é obrigatória")
        @Pattern(
                regexp = "^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$",
                message = "UF inválido"
        )
        private String uf;

        @NotBlank(message = "O CEP é obrigatório")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#####-###")
        private String cep;


        @PastOrPresent(message = "A data de criação não pode ser no futuro")
        @NotNull(message = "A data de criação é obrigatória")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate dataCriacao;

        @NotNull(message = "O status é obrigatório")
        private Boolean status;

        public void setCep(@NotNull(message = "O CEP é obrigatório") String cep) {
            this.cep = cep;
        }
}
