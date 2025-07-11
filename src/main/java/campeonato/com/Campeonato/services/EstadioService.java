package campeonato.com.Campeonato.services;

import campeonato.com.Campeonato.dto.ClubeRequestDTO;
import campeonato.com.Campeonato.exception.ClubeExisteException;
import campeonato.com.Campeonato.exception.ClubeNaoEncontradoException;
import campeonato.com.Campeonato.model.Clube;
import campeonato.com.Campeonato.repository.ClubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class EstadioService {

    @Autowired
    private EstadioRepository estadioRepository;

    public String cadastrarEstadio(EstadioRequestDTO estadioRequestDTO) {
        boolean jaExiste = estadioRepository
                .findByNomeAndUfIgnoreCase(estadioRequestDTO.getNome(), estadioRequestDTO.getUf())
                .isPresent();

        if (jaExiste) {
            throw new EstadioExisteException("Já existe um estadio com esse nome nesse estado.");
        }

        Estadio estadio = new Estadio();
        estadio.setNome(estadioRequestDTO.getNome());
        estadio.setUf(estadioRequestDTO.getUf());
        estadio.setDataCriacao(estaioRequestDTO.getDataCriacao());
        estadio.setStatus(estadioRequestDTO.getStatus());

        estadioRepository.save(estadio);

        return "Estadio " + estadio.getNome() + " cadastrado com sucesso!";
    }

    public String atualizarEstadio(Long id, EstadioRequestDTO dto) {
        Estadio estadio = estadioRepository.findById(id)
                .orElseThrow(() -> new EstadioNaoEncontradoException("Estadio não encontrado!"));

        estadioRepository.findByNomeAndUfIgnoreCase(dto.getNome(), dto.getUf())
                .filter(outroEstadio -> !outroEstadio.getId().equals(id))
                .ifPresent(outroEstadio -> {
                    throw new EstadioExisteException("Já existe um estadio com esse nome nesse estado.");
                });

        estadio.setNome(dto.getNome());
        estadio.setUf(dto.getUf());
        estadio.setDataCriacao(dto.getDataCriacao());
        estadio.setStatus(dto.getStatus());

        estadioRepository.save(estadio);
        return "Estadio atualizado com sucesso!";
    }

    public void inativarEstadio(Long id) {
        Estadio estadio = estadioRepository.findById(id)
                .orElseThrow(() -> new EstadioNaoEncontradoException("Estadio não encontrado!"));

        if (!Boolean.FALSE.equals(estadio.getStatus())) {
            estadio.setStatus(false);
            estadioRepository.save(estadio);
        }
    }

    public Estadio buscarEstadioPorId(Long id) {
        return estadioRepository.findById(id).orElseThrow(() ->
                new EstadioNaoEncontradoException("Estadio não encontrado!"));
    }


    public Page<Estadio> listarEstadio(String nome, String uf, Boolean status, Pageable pageable) {
        List<Estadio> estadios = estadioRepository.findAll();

        if (nome != null) {
            estadios = estadios.stream()
                    .filter(c -> c.getNome().toLowerCase().contains(nome.toLowerCase()))
                    .toList();
        }
        if (uf != null) {
            estadios = estadios.stream()
                    .filter(c -> c.getUf().equalsIgnoreCase(uf))
                    .toList();
        }
        if (status != null) {
            estadios = estadios.stream()
                    .filter(c -> c.getStatus().equals(status))
                    .toList();
        }


        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), estadios.size());
        List<Estadio> pagina = (start >= estadios.size()) ? List.of() : estadios.subList(start, end);

        return new PageImpl<>(pagina, pageable, estadios.size());
    }
}

}
