package campeonato.com.Campeonato.DoMain.Specifications;

import org.springframework.data.jpa.domain.Specification;
import campeonato.com.Campeonato.DoMain.Entities.Clubes;

public class ClubesSpecifications {

    private ClubesSpecifications() {
    }

    public static Specification<Clubes> nomeContem(String nome) {
        return (root, query, cb) -> nome == null ? null : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }
    public static Specification<Clubes> ufIgual(String uf) {
        return (root, query, cb) -> uf == null ? null : cb.equal(cb.lower(root.get("uf")), uf.toLowerCase());
    }
    public static Specification<Clubes> statusIgual(Boolean status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

}
