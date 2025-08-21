package campeonato.com.Campeonato.adapters.outbound.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "clube")
public class JpaEntityClube {

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

    public JpaEntityClube() {}

    public JpaEntityClube(campeonato.com.Campeonato.domain.entities.Clube clube) {
        this.id = clube.getId();
        this.nome = clube.getNome();
        this.uf = clube.getUf();
        this.dataCriacao = clube.getDataCriacao();
        this.status = clube.getAtivo();
    }

    public campeonato.com.Campeonato.domain.entities.Clube toDomain() {
        return new campeonato.com.Campeonato.domain.entities.Clube(
                this.id,
                this.nome,
                this.uf,
                this.dataCriacao,
                this.status
        );
    }

    public static JpaEntityClube fromDomain(campeonato.com.Campeonato.domain.entities.Clube clube) {
        return new JpaEntityClube(clube);
    }
}