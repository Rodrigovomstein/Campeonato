package campeonato.com.Campeonato.domain.specifications;

import org.springframework.data.jpa.domain.Specification;
import campeonato.com.Campeonato.adapters.outbound.entities.JpaEntityClube;

public class ClubeSpecifications {

    private ClubeSpecifications() {
    }

    public static Specification<JpaEntityClube> nomeContem(String nome) {
        return (root, query, cb) -> nome == null ? null : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }
    public static Specification<JpaEntityClube> ufIgual(String uf) {
        return (root, query, cb) -> uf == null ? null : cb.equal(cb.lower(root.get("uf")), uf.toLowerCase());
    }
    public static Specification<JpaEntityClube> statusIgual(Boolean status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

}