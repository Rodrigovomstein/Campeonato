package campeonato.com.Campeonato.Specifications;

import campeonato.com.Campeonato.model.Clube;
import org.springframework.data.jpa.domain.Specification;

public class ClubeSpecifications {

    public static Specification<Clube> nomeContem(String nome) {
        return (root, query, cb) -> nome == null ? null : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }
    public static Specification<Clube> ufIgual(String uf) {
        return (root, query, cb) -> uf == null ? null : cb.equal(cb.lower(root.get("uf")), uf.toLowerCase());
    }
    public static Specification<Clube> statusIgual(Boolean status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }
}
