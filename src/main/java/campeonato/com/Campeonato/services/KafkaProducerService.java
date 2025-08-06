package campeonato.com.Campeonato.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "seu-topico";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void enviarMensagem(String mensagem) {
        kafkaTemplate.send(TOPIC, mensagem);
    }

    public void enviarCadastroPartida(String mensagem) {
    }
}

