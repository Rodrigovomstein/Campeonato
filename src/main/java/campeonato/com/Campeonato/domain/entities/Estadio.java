package campeonato.com.Campeonato.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Estadio {

    @Id
    private Long id;

    private String nome;

    private String uf;

    private String cep;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    private Boolean status;

    public Estadio () {
    }

    public Estadio(Long id, String nome, String uf, String cep, LocalDate dataCriacao, Boolean status) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
        this.cep = cep;
        this.dataCriacao = dataCriacao;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}