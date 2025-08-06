package campeonato.com.Campeonato.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import campeonato.com.Campeonato.dto.ViaCepDto;

@Service
public class ViaCepService {

    private static final String VIACEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    private final RestTemplate restTemplate = new RestTemplate();

    public ViaCepDto buscarEnderecoPorCep(String cep) {
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