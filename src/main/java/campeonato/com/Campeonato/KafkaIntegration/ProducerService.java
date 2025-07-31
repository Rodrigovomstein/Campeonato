package campeonato.com.Campeonato.KafkaIntegration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService<Mensagem> {

    private final KafkaTemplate<String, Mensagem> kafkaTemplate;

    private static final String TOPIC = "meu_topico";

    public ProducerService(KafkaTemplate<String, Mensagem> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMensagem(Mensagem mensagem) {
        kafkaTemplate.send(TOPIC, mensagem);
    }
}