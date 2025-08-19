package campeonato.com.Campeonato.Adapters.OutBound.KafkaIntegration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MensagemKafka {

    @JsonProperty
    private String conteudo;


    public MensagemKafka() {

    }

    public MensagemKafka(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public String toString() {
        return "MensagemKafka{" +
                "conteudo='" + conteudo + '\'' +
                '}';
    }
}