package campeonato.com.Campeonato.configuration;

import campeonato.com.Campeonato.adapters.outbound.messaging.KafkaPublisherAdapter;
import campeonato.com.Campeonato.application.usecases.KafkaUseCaseImpl;
import campeonato.com.Campeonato.domain.ports.inbound.KafkaUseCasePort;
import campeonato.com.Campeonato.domain.ports.outbound.KafkaPublisherPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    @Bean
    public KafkaPublisherPort kafkaPublisherPort(KafkaPublisherAdapter kafkaPublisherAdapter) {
        return kafkaPublisherAdapter;
    }

    @Bean
    public KafkaUseCasePort kafkaUseCasePort(KafkaPublisherPort kafkaPublisherPort) {
        return new KafkaUseCaseImpl(kafkaPublisherPort);
    }
}