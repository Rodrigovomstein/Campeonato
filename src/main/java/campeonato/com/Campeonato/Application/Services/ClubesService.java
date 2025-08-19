package campeonato.com.Campeonato.Application.Services;

import campeonato.com.Campeonato.Adapters.InBound.Dto.ClubesRequestDTO;
import campeonato.com.Campeonato.DoMain.Entities.Clubes;
import campeonato.com.Campeonato.DoMain.Exception.ClubesExisteException;
import campeonato.com.Campeonato.DoMain.Exception.ClubesNaoEncontradoException;
import campeonato.com.Campeonato.DoMain.Model.Clube;
import campeonato.com.Campeonato.DoMain.Repository.JpaClubesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import campeonato.com.Campeonato.DoMain.Specifications.ClubesSpecifications;
import org.springframework.data.jpa.domain.Specification;


@Service
public class ClubesService {

    @Autowired
    private JpaClubesRepository jpaClubesRepository;

    public ClubesService(JpaClubesRepository jpaClubesRepository) {

    }


    public String cadastrarClubes(ClubesRequestDTO clubesRequestDTO) {
        validarClubes(clubesRequestDTO);
        return "Clubes " + clubesRequestDTO.getNome() + " cadastrado com sucesso!";
    }

    public String validarClubes(ClubesRequestDTO clubesRequestDTO) {
        boolean jaExiste = jpaClubesRepository
                .findByNomeAndUfIgnoreCase(clubesRequestDTO.getNome(), clubesRequestDTO.getUf())
                .isPresent();

        if (jaExiste) {
            throw new ClubesExisteException("Já existe um clube com esse nome nesse estado.");
        }

        if (clubesRequestDTO.getNome() == null || clubesRequestDTO.getNome().isEmpty()) {
            throw new RuntimeException("O nome do clube não pode ser vazio.");
        }

        Clubes clubes = new Clubes();
        clubes.setNome(clubesRequestDTO.getNome());
        clubes.setUf(clubesRequestDTO.getUf());
        clubes.setDataCriacao(clubesRequestDTO.getDataCriacao());
        clubes.setStatus(clubesRequestDTO.getStatus());

        jpaClubesRepository.save(clubes);

        return "Clube " + clubes.getNome() + " cadastrado com sucesso!";
    }

    public String atualizarClubes(Long id, ClubesRequestDTO dto) {
        Clubes clubes = null;
        try {
            clubes = (Clubes) jpaClubesRepository.findById(id)
                    .orElseThrow(() -> new ClubesNaoEncontradoException("Clube não encontrado!"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        jpaClubesRepository.findByNomeAndUfIgnoreCase(dto.getNome(), dto.getUf())
                .filter(outroClube -> !outroClube.getId().equals(id))
                .ifPresent(outroClube -> {
                    throw new ClubesExisteException("Já existe um clube com esse nome nesse estado.");
                });

        clubes.setNome(dto.getNome());
        clubes.setUf(dto.getUf());
        clubes.setDataCriacao(dto.getDataCriacao());
        clubes.setStatus(dto.getStatus());

        jpaClubesRepository.save(clubes);
        return "Clube atualizado com sucesso!";
    }

    public void inativarClube(Long id) throws Throwable {
        Clubes clubes = (Clubes) jpaClubesRepository.findById(id)
                .orElseThrow()-> new ClubesNaoEncontradoException;


        if (!Boolean.FALSE.equals(clubes.getStatus())) {
            clubes.setStatus(false);
            jpaClubesRepository.save(clubes);
        }
    }

    public Clubes buscarClubesPorId(Long id) {
        try {
            return jpaClubesRepository.findById(id)
                    .orElseThrow(() ->
                    new ClubesNaoEncontradoException("Clube não encontrado!"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Page<Clubes> listarClubes(String nome, String uf, Boolean status, Pageable pageable) {
        Specification<Clubes> spec = ClubesSpecifications.nomeContem(nome)
                .and(ClubesSpecifications.ufIgual(uf))
                .and(ClubesSpecifications.statusIgual(status));
        return jpaClubesRepository.findAll(spec, pageable);
    }

    public Object buscarClubesAvancado(String flamengo, String rj, Object o) {
        Specification<Clubes> spec = ClubesSpecifications.nomeContem(flamengo)
                .and(ClubesSpecifications.ufIgual(rj))
                .and(ClubesSpecifications.statusIgual(Boolean.TRUE));
        return jpaClubesRepository.findAll(spec);
    }
}


