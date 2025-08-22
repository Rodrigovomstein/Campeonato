package campeonato.com.Campeonato.domain.entities;

import campeonato.com.Campeonato.domain.valueobjects.Endereco;
import java.time.LocalDate;
import java.util.Objects;

public class Estadio {

    private Long id;
    private String nome;
    private Endereco endereco;
    private LocalDate dataCriacao;
    private Boolean ativo;

    public Estadio(String nome, Endereco endereco) {
        this.id = id;
        this.nome = validarNome(nome);
        this.endereco = validarEndereco(endereco);
        this.dataCriacao = validarDataCriacao(dataCriacao);
        this.ativo = ativo != null ? ativo : true;
    }

    public Estadio(String nome, Endereco endereco, LocalDate dataCriacao) {
        this(nome, endereco);
    }

    // Construtor para compatibilidade com versão anterior (será removido gradualmente)
    public Estadio(Long id, String nome, String uf, LocalDate dataCriacao, Boolean ativo) {
        this(nome, new Endereco("00000-000", uf));
    }

    private String validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do estadio não pode ser vazio");
        }
        if (nome.length() > 100) {
            throw new IllegalArgumentException("Nome do estadio não pode ter mais de 100 caracteres");
        }
        return nome.trim();
    }

    private Endereco validarEndereco(Endereco endereco) {
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }
        return endereco;
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

    public Estadio inativar() {
        return new Estadio(this.nome, this.endereco);
    }

    public Estadio ativar() {
        return new Estadio(this.nome, this.endereco);
    }

    public Estadio atualizar(String novoNome, Endereco novoEndereco, LocalDate novaDataCriacao) {
        return new Estadio(novoNome, novoEndereco);
    }

    public Estadio atualizarEndereco(Endereco novoEndereco) {
        return new Estadio(this.nome, novoEndereco);
    }

    public boolean isAtivo() {
        return Boolean.TRUE.equals(this.ativo);
    }

    public boolean isEnderecoCompleto() {
        return endereco.isCompleto();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    // Métodos de compatibilidade (serão removidos gradualmente)
    public String getUf() {
        return endereco.getUf();
    }

    public String getCep() {
        return endereco.getCep();
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estadio estadio = (Estadio) o;
        return Objects.equals(id, estadio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Estadio{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", endereco=" + endereco +
                ", dataCriacao=" + dataCriacao +
                ", ativo=" + ativo +
                '}';
    }
}