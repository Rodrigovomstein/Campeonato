package campeonato.com.Campeonato.services;

import campeonato.com.Campeonato.dto.EstadioRequestDto;
import campeonato.com.Campeonato.dto.ViaCepDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class EstadioViaCepClient {

    private static final String VIACEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    private final RestTemplate restTemplate = new RestTemplate();

    public String cadastrarEstadio(EstadioRequestDto dto) {
        ViaCepDto endereco = buscarEnderecoPorCep(dto.getCep());
        if (endereco == null) {
            throw new RuntimeException("Endereço não encontrado!");
        }
        return "Estadio " + dto.getNome() + " cadastrado com sucesso!";
    }

    public ViaCepDto buscarEnderecoPorCep(@NotNull(message = "O CEP é obrigatório") String cep) {
        try {
            ViaCepDto response = restTemplate.getForObject(VIACEP_URL, ViaCepDto.class, cep);
            if (response == null || response.getCep() == null) {
                throw new RuntimeException("CEP não encontrado!");
            }
            return response;
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erro ao consultar ViaCEP: " + e.getMessage());
        }
    }
}