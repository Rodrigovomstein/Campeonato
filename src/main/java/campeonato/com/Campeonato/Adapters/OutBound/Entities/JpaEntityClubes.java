package campeonato.com.Campeonato.Adapters.OutBound.Entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "clubes")
public class JpaEntityClubes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name = "estado")
    private String uf;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "ativo")
    private Boolean status;

    public JpaEntityClubes() {}

    public JpaEntityClubes(campeonato.com.Campeonato.DoMain.Entities.Clubes clubes) {
        this.id = clubes.getId();
        this.nome = clubes.getNome();
        this.uf = clubes.getUf();
        this.dataCriacao = clubes.getDataCriacao();
        this.status = clubes.getStatus();
    }


}
