package campeonato.com.Campeonato.domain.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Clube {

    private final Long id;
    private final String nome;
    private final String uf;
    private final LocalDate dataCriacao;
    private final Boolean ativo;

    public Clube(Long id, String nome, String uf, LocalDate dataCriacao, Boolean ativo) {
        this.id = id;
        this.nome = validarNome(nome);
        this.uf = validarUf(uf);
        this.dataCriacao = validarDataCriacao(dataCriacao);
        this.ativo = ativo != null ? ativo : true;
    }

    public Clube(String nome, String uf, LocalDate dataCriacao) {
        this(null, nome, uf, dataCriacao, true);
    }

    private String validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do clube não pode ser vazio");
        }
        if (nome.length() > 100) {
            throw new IllegalArgumentException("Nome do clube não pode ter mais de 100 caracteres");
        }
        return nome.trim();
    }

    private String validarUf(String uf) {
        if (uf == null || uf.trim().isEmpty()) {
            throw new IllegalArgumentException("UF não pode ser vazia");
        }
        if (uf.length() != 2) {
            throw new IllegalArgumentException("UF deve ter exatamente 2 caracteres");
        }
        return uf.toUpperCase().trim();
    }

    private LocalDate validarDataCriacao(LocalDate dataCriacao) {
        if (dataCriacao == null) {
            throw new IllegalArgumentException("Data de criação não pode ser nula");
        }
        if (dataCriacao.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de criação não pode ser futura");
        }
        return dataCriacao;
    }

    public Clube inativar() {
        return new Clube(this.id, this.nome, this.uf, this.dataCriacao, false);
    }

    public Clube ativar() {
        return new Clube(this.id, this.nome, this.uf, this.dataCriacao, true);
    }

    public Clube atualizar(String novoNome, String novaUf, LocalDate novaDataCriacao) {
        return new Clube(this.id, novoNome, novaUf, novaDataCriacao, this.ativo);
    }

    public boolean isAtivo() {
        return Boolean.TRUE.equals(this.ativo);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getUf() {
        return uf;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return ativo;
        if (o == null || getClass() != o.getClass()) return false;
        Clube clube = (Clube) o;
        return Objects.equals(id, clube.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Clube{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", uf='" + uf + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", ativo=" + ativo +
                '}';
    }
}
