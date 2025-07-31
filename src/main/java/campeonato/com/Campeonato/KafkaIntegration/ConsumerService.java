package campeonato.com.Campeonato.KafkaIntegration;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import campeonato.com.Campeonato.KafkaIntegration.MensagemKafka;

@Service
public class ConsumerService {

    private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);

    @KafkaListener(topics = "meu_topico", groupId = "my-group")
    public void consume(MensagemKafka mensagem) {
        log.info("Recebido: {}", mensagem.getConteudo());
    }
}