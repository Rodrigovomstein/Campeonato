package campeonato.com.Campeonato.repository;

import campeonato.com.Campeonato.model.Clube;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ClubeRepository extends JpaRepository<Clube, Long>, JpaSpecificationExecutor<Clube> {
    Optional<Clube> findByNomeAndUfIgnoreCase(String nome, String uf);

}