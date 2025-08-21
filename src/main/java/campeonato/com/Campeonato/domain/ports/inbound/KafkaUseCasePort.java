package campeonato.com.Campeonato.domain.ports.inbound;

public interface KafkaUseCasePort {

    void enviarMensagem(String mensagem);

}