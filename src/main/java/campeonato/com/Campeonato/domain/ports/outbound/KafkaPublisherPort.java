package campeonato.com.Campeonato.domain.ports.outbound;

public interface KafkaPublisherPort {

    void publicarMensagem(String mensagem);

}