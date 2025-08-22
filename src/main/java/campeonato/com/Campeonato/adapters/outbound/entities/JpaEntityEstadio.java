package campeonato.com.Campeonato.adapters.outbound.entities;

import campeonato.com.Campeonato.domain.entities.Estadio;
import campeonato.com.Campeonato.domain.valueobjects.Endereco;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name= "estadio")
public class JpaEntityEstadio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name = "cep")
    private String cep;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "localidade")
    private String localidade;

    @Column(name = "estado")
    private String uf;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "ativo")
    private Boolean status;

    public JpaEntityEstadio() {
    }

    public JpaEntityEstadio(Long id, String nome, String cep, String logradouro, String bairro,
                            String localidade, String uf, LocalDate dataCriacao, Boolean status) {
        this.id = id;
        this.nome = nome;
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.dataCriacao = dataCriacao;
        this.status = status;
    }

    public static JpaEntityEstadio fromDomain(Estadio estadio) {
        Endereco endereco = estadio.getEndereco();
        return new JpaEntityEstadio(
                estadio.getId(),
                estadio.getNome(),
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getBairro(),
                endereco.getLocalidade(),
                endereco.getUf(),
                estadio.getDataCriacao(),
                estadio.getAtivo()
        );
    }

    public Estadio toDomain() {
        Endereco endereco = new Endereco(
                this.cep,
                this.logradouro,
                this.bairro,
                this.localidade,
                this.uf
        );

        return new Estadio(
                this.nome,
                endereco
        );
    }
}

