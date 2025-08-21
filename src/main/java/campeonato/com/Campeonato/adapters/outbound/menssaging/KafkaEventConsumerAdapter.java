package campeonato.com.Campeonato.adapters.outbound.menssaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class KafkaEventConsumerAdapter {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventConsumerAdapter.class);

    @KafkaListener(topics = "meu_topico", groupId = "my-group")
    public void consume(MensagemKafka mensagem) {
        log.info("Recebido: {}", mensagem.getConteudo());
    }
}