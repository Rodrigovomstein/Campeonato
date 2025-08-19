package campeonato.com.Campeonato.Adapters.OutBound.repository;

import campeonato.com.Campeonato.Adapters.OutBound.RepositoryImpl.Clubes;
import campeonato.com.Campeonato.DoMain.Repository.JpaClubesRepository;

import java.util.List;

public class ClubesRepositoryImpl implements JpaClubesRepository {

    private final JpaClubesRepository jpaClubesRepository;

    public ClubesRepositoryImpl(JpaClubesRepository jpaClubesRepository) {
        this.jpaClubesRepository = jpaClubesRepository;
    }

    @Override
    public Object cadastroClubes(Clubes clubes) {
        Clubes clubesRequestDTO;
        validarClubes(clubesRequestDTO);
        return "Clube " + clubesRequestDTO.getNome() + " cadastrado com sucesso!";
        }
        return jpaClubesRepository.save(clubes);
    }

    @Override
    public Object validarClubes(Clubes clubes) {

    }

    @Override



}
