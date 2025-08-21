package campeonato.com.Campeonato.configuration;

import campeonato.com.Campeonato.adapters.outbound.external.ViaCepServiceAdapter;
import campeonato.com.Campeonato.domain.ports.outbound.ViaCepServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ViaCepConfiguration {

    @Bean
    public ViaCepServicePort viaCepServicePort(ViaCepServiceAdapter viaCepServiceAdapter) {
        return viaCepServiceAdapter;
    }
}