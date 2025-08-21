package campeonato.com.Campeonato.adapters.outbound.external;

import campeonato.com.Campeonato.adapters.inbound.dto.ViaCepDto;
import campeonato.com.Campeonato.domain.ports.outbound.ViaCepServicePort;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ViaCepServiceAdapter implements ViaCepServicePort {

    private static final String VIACEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ViaCepDto buscarEnderecoPorCep(@NotNull String cep) {
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