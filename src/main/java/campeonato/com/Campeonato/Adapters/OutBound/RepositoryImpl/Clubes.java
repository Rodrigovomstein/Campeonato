package campeonato.com.Campeonato.Adapters.OutBound.RepositoryImpl;

import java.util.List;

public abstract class Clubes {

    Clubes save(Clubes clubes) {
        return null;
    }

    Clubes findById(Long id) {
        return null;
    }

    Clubes findByNome(String nome) {
        return null;
    }

    Clubes findByNomeAndUf(String nome, String uf) {
        return null;
    }

    List<Clubes> findAll() {
        return null;
    }

    List<Clubes> findAllByNome(String nome) {
        return null;
    }

    List<Clubes> findAllByUf(String uf) {
        return null;
    }

    List<Clubes> findAllByStatus(Boolean status) {
        return null;
    }

    void delete(Clubes clubes) {
    }

    void deleteById(Long id) {
    }

    void deleteByNome(String nome) {
    }

    void deleteByUf(String uf) {
    }

    void deleteByStatus(Boolean status) {
    }

    void deleteAll() {
    }

}
