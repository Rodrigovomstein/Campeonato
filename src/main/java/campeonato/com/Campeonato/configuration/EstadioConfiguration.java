package campeonato.com.Campeonato.configuration;

import campeonato.com.Campeonato.adapters.outbound.persistence.EstadioRepositoryAdapter;
import campeonato.com.Campeonato.adapters.outbound.repository.JpaEstadioRepository;
import campeonato.com.Campeonato.application.usecases.EstadioUseCaseImpl;
import campeonato.com.Campeonato.domain.ports.inbound.EstadioUseCasePort;
import campeonato.com.Campeonato.domain.ports.outbound.EstadioRepositoryPort;
import campeonato.com.Campeonato.domain.ports.outbound.ViaCepServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EstadioConfiguration {

    @Bean
    public EstadioRepositoryPort estadioRepositoryPort(JpaEstadioRepository jpaEstadioRepository) {
        return new EstadioRepositoryAdapter(jpaEstadioRepository);
    }

    @Bean
    public EstadioUseCasePort estadioUseCasePort(EstadioRepositoryPort estadioRepositoryPort,
                                                 ViaCepServicePort viaCepServicePort) {
        return new EstadioUseCaseImpl(estadioRepositoryPort, viaCepServicePort);
    }
}