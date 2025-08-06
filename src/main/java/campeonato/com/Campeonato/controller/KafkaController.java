package campeonato.com.Campeonato.controller;


import campeonato.com.Campeonato.services.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public KafkaController(KafkaProducerService producerService) {
        this.kafkaProducerService = producerService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> enviar(@RequestParam String mensagem) {
        kafkaProducerService.enviarMensagem(mensagem);
        return ResponseEntity.ok("Mensagem enviada!");
    }
}
