package campeonato.com.Campeonato.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "partida")
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estadio;
    private String uf;
    private String clube1Id;
    private String clube2Id;

    @Column(name = "data_e_horario")
    private LocalDateTime dataHorario;

    @Column(name = "ativo")
    private Boolean status;
}