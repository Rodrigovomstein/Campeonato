package campeonato.com.Campeonato.DoMain.Entities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("campeonato.com.Campeonato.DoMain.Entities")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}