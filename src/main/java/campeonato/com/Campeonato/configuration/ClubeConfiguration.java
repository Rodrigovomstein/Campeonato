package campeonato.com.Campeonato.configuration;

import campeonato.com.Campeonato.adapters.outbound.persistence.ClubeRepositoryAdapter;
import campeonato.com.Campeonato.adapters.outbound.repository.JpaClubeRepository;
import campeonato.com.Campeonato.application.usecases.ClubeUseCaseImpl;
import campeonato.com.Campeonato.domain.ports.inbound.ClubeUseCasePort;
import campeonato.com.Campeonato.domain.ports.outbound.ClubeRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClubeConfiguration {

    @Bean
    public ClubeRepositoryPort clubeRepositoryPort(JpaClubeRepository jpaClubeRepository) {
        return new ClubeRepositoryAdapter(jpaClubeRepository);
    }

    @Bean
    public ClubeUseCasePort clubeUseCasePort(ClubeRepositoryPort clubeRepositoryPort) {
        return new ClubeUseCaseImpl(clubeRepositoryPort);
    }
}