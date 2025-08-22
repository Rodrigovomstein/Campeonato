package campeonato.com.Campeonato.domain.valueobjects;

import java.util.Objects;

public class Endereco {

    private final String cep;
    private final String logradouro;
    private final String bairro;
    private final String localidade;
    private final String uf;

    public Endereco(String cep, String logradouro, String bairro, String localidade, String uf) {
        this.cep = validarCep(cep);
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = validarUf(uf);
    }

    public Endereco(String cep, String uf) {
        this(cep, null, null, null, uf);
    }

    private String validarCep(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            throw new IllegalArgumentException("CEP não pode ser vazio");
        }

        String cepLimpo = cep.replaceAll("[^0-9]", "");
        if (cepLimpo.length() != 8) {
            throw new IllegalArgumentException("CEP deve ter 8 dígitos");
        }

        return formatarCep(cepLimpo);
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

    private String formatarCep(String cep) {
        return cep.substring(0, 5) + "-" + cep.substring(5);
    }

    public boolean isCompleto() {
        return logradouro != null && !logradouro.trim().isEmpty() &&
                bairro != null && !bairro.trim().isEmpty() &&
                localidade != null && !localidade.trim().isEmpty();
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getUf() {
        return uf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(cep, endereco.cep) &&
                Objects.equals(uf, endereco.uf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cep, uf);
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "cep='" + cep + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", bairro='" + bairro + '\'' +
                ", localidade='" + localidade + '\'' +
                ", uf='" + uf + '\'' +
                '}';
    }
}
