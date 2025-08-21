package campeonato.com.Campeonato.application.usecases;

import campeonato.com.Campeonato.domain.ports.inbound.KafkaUseCasePort;
import campeonato.com.Campeonato.domain.ports.outbound.KafkaPublisherPort;

public class KafkaUseCaseImpl implements KafkaUseCasePort {

    private final KafkaPublisherPort kafkaPublisherPort;

    public KafkaUseCaseImpl(KafkaPublisherPort kafkaPublisherPort) {
        this.kafkaPublisherPort = kafkaPublisherPort;
    }

    @Override
    public void enviarMensagem(String mensagem) {
        if (mensagem == null || mensagem.trim().isEmpty()) {
            throw new IllegalArgumentException("Mensagem não pode ser vazia");
        }

        kafkaPublisherPort.publicarMensagem(mensagem);
    }
}