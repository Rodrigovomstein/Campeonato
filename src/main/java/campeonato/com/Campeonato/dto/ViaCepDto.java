package campeonato.com.Campeonato.dto;

import jakarta.validation.constraints.NotNull;

public class ViaCepDto {

    public @NotNull(message = "O CEP é obrigatório") String getCep() {
        @NotNull(message = "O CEP é obrigatório") String cep = new String();
        return cep;
    }

    public class EnderecoResponse {
        private String logradouro;
        private String bairro;
        private String localidade;
        private String uf;
        private String cep;

    }
}


