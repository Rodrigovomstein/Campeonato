package campeonato.com.Campeonato.KafkaIntegration;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController<Mensagem> {

    private final ProducerService producerService;

    public KafkaController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/enviar")
    public void enviarMensagem(@RequestBody Mensagem mensagem) {
        producerService.sendMensagem(mensagem);
    }
}