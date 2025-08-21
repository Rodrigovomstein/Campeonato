package campeonato.com.Campeonato.adapters.outbound.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name= "estadios")
public class JpaEntityEstadios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name = "estado")
    private String uf;

    @Column(name = "cep")
    private String cep;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "ativo")
    private Boolean status;

    public JpaEntityEstadios() {
    }

    public JpaEntityEstadios(Long id, String nome, String uf, String cep, LocalDate dataCriacao, Boolean status) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
        this.cep = cep;
        this.dataCriacao = dataCriacao;
        this.status = status;
    }

}

