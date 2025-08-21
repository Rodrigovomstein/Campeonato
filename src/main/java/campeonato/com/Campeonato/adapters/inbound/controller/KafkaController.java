package campeonato.com.Campeonato.adapters.inbound.controller;

import campeonato.com.Campeonato.domain.ports.inbound.KafkaUseCasePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaUseCasePort kafkaUseCasePort;

    public KafkaController(KafkaUseCasePort kafkaUseCasePort) {
        this.kafkaUseCasePort = kafkaUseCasePort;
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> enviar(@RequestParam String mensagem) {
        try {
            kafkaUseCasePort.enviarMensagem(mensagem);
            return ResponseEntity.ok("Mensagem enviada com sucesso!");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}