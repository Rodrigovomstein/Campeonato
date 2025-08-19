package campeonato.com.Campeonato.DoMain.Entities;

import java.time.LocalDate;

public class Clubes {

    private Long id;

    private String nome;

    private String uf;

    private LocalDate dataCriacao;

    private Boolean status;

    public Clubes () {
    }

    public Clubes(Long id, String nome, String uf, LocalDate dataCriacao, Boolean status) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
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
