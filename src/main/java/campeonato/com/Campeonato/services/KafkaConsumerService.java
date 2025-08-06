package campeonato.com.Campeonato.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "meu_topico", groupId = "my-group")
    public void consumirMensagem(String mensagem) {
        System.out.println("Mensagem recebida do Kafka: " + mensagem);
    }
}
