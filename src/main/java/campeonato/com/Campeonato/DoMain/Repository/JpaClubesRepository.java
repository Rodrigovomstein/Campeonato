package campeonato.com.Campeonato.DoMain.Repository;

import campeonato.com.Campeonato.Adapters.OutBound.Entities.JpaEntityClubes;
import campeonato.com.Campeonato.DoMain.Entities.Clubes;
import campeonato.com.Campeonato.DoMain.Model.Clube;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

@Entity
public interface JpaClubesRepository extends JpaRepository<JpaEntityClubes>, JpaSpecificationExecutor<Clubes> {
    Optional<Clubes> findByNomeAndUfIgnoreCase(String nome, String uf);

    Object cadastroClube(Clubes clubes);

}