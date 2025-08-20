package campeonato.com.Campeonato.Specifications;

import campeonato.com.Campeonato.model.Partida;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class PartidaSpecifications {

    public static Specification<Partida> ufIgual(String uf) {
        return (root, query, cb) -> uf == null ? null : cb.equal(cb.lower(root.get("uf")), uf.toLowerCase());
    }

    public static Specification<Partida> statusIgual(Boolean status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Partida> dataHorarioIgual(LocalDate dataHorario) {
        return (root, query, cb) -> dataHorario == null ? null : cb.equal(root.get("dataHorario"), dataHorario);
    }

    public static Specification<Partida> estadioContem(String estadio) {
        return (root, query, cb) -> estadio == null ? null : cb.like(cb.lower(root.get("estadio")), "%" + estadio.toLowerCase() + "%");
    }

    public static Specification<Partida> clube1Igual(String clube1Id) {
        return (root, query, cb) -> clube1Id == null ? null : cb.like(cb.lower(root.get("clube1")), "%" + clube1Id.toLowerCase() + "%");
    }

    public static Specification<Partida> clube2Igual(String clube2Id) {
        return (root, query, cb) -> clube2Id == null ? null : cb.like(cb.lower(root.get("clube2")), "%" + clube2Id.toLowerCase() + "%");
    }
}
