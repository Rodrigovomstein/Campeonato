package campeonato.com.Campeonato.adapters.outbound.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "partidas")
public class JpaEntityPartidas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estadio;
    private String uf;
    private Long clube1Id;
    private Long clube2Id;

    @Column(name = "data_e_horario")
    private LocalDateTime dataHorario;

    @Column(name = "ativo")
    private Boolean status;

    private Integer golsClube1;
    private Integer golsClube2;

    public JpaEntityPartidas() {
    }

    public JpaEntityPartidas(Long id, String estadio, String uf, Long clube1Id, Long clube2Id, LocalDateTime dataHorario, Boolean status, Integer golsClube1, Integer golsClube2) {
        this.id = id;
        this.estadio = estadio;
        this.uf = uf;
        this.clube1Id = clube1Id;
        this.clube2Id = clube2Id;
        this.dataHorario = dataHorario;
        this.status = status;
        this.golsClube1 = golsClube1;
        this.golsClube2 = golsClube2;
    }

}

