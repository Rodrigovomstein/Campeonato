package campeonato.com.Campeonato.domain.specifications;

import campeonato.com.Campeonato.adapters.outbound.entities.JpaEntityClube;
import campeonato.com.Campeonato.adapters.outbound.entities.JpaEntityEstadio;
import campeonato.com.Campeonato.domain.entities.Estadio;
import org.springframework.data.jpa.domain.Specification;

public class EstadioSpecifications {

    private EstadioSpecifications() {
    }

    public static Specification<JpaEntityEstadio> nomeContem(String nome) {
        return (root, query, cb) -> nome == null ? null : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }
    public static Specification<JpaEntityEstadio> ufIgual(String uf) {
        return (root, query, cb) -> uf == null ? null : cb.equal(cb.lower(root.get("uf")), uf.toLowerCase());
    }
    public static Specification<JpaEntityEstadio> statusIgual(Boolean status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

}