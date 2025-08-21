package campeonato.com.Campeonato.domain.ports.outbound;

import campeonato.com.Campeonato.adapters.inbound.dto.ViaCepDto;

public interface ViaCepServicePort {

    ViaCepDto buscarEnderecoPorCep(String cep);

}