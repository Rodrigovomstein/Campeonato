package campeonato.com.Campeonato.adapters.outbound.messaging;

import campeonato.com.Campeonato.domain.ports.outbound.KafkaPublisherPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPublisherAdapter implements KafkaPublisherPort {

    private static final String TOPIC = "campeonato-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publicarMensagem(String mensagem) {
        kafkaTemplate.send(TOPIC, mensagem);
    }
}