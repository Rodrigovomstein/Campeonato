package campeonato.com.Campeonato.application.usecases;

import campeonato.com.Campeonato.domain.ports.outbound.ViaCepServicePort;
import campeonato.com.Campeonato.domain.specifications.EstadioSpecifications;
import campeonato.com.Campeonato.adapters.inbound.dto.EstadioRequestDto;
import campeonato.com.Campeonato.domain.exception.EstadioExisteException;
import campeonato.com.Campeonato.domain.exception.EstadioNaoEncontradoException;
import campeonato.com.Campeonato.domain.entities.Estadio;
import campeonato.com.Campeonato.domain.ports.EstadiosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class EstadioUseCaseImpl {

    @Autowired
    private EstadiosRepository estadiosRepository;
    private ViaCepServicePort viaCepServicePort;


    public String cadastrarEstadio(EstadioRequestDto estadioRequestDto) {
        validarEstadio(estadioRequestDto);
        return "Estadio " + estadioRequestDto.getNome() + " cadastrado com sucesso!";
    }

    public String validarEstadio(EstadioRequestDto estadioRequestDto) {
        boolean jaExiste = estadiosRepository
                .findByNomeAndUfIgnoreCase(estadioRequestDto.getNome(), estadioRequestDto.getUf())
                .isPresent();

        if (jaExiste) {
            throw new EstadioExisteException("Já existe um estadio com esse nome nesse estado.");
        }

        if (estadioRequestDto.getNome() == null || estadioRequestDto.getNome().isEmpty()) {
            throw new RuntimeException("O nome do clube não pode ser vazio.");
        }

        Estadio estadio = new Estadio();
        estadio.setNome(estadioRequestDto.getNome());
        estadio.setUf(estadioRequestDto.getUf());
        estadio.setCep(estadioRequestDto.getCep());
        estadio.setDataCriacao(estadioRequestDto.getDataCriacao());
        estadio.setStatus(estadioRequestDto.getStatus());

        estadiosRepository.save(estadio);

        return "Estadio " + estadio.getNome() + " cadastrado com sucesso!";
    }

    public String atualizarEstadio(Long id, EstadioRequestDto dto) {
        Estadio estadio = estadiosRepository.findById(id)
                .orElseThrow(() -> new EstadioNaoEncontradoException("Estadio não encontrado!"));

        estadiosRepository.findByNomeAndUfIgnoreCase(dto.getNome(), dto.getUf())
                .filter( outroEstadio -> !outroEstadio.getId().equals(id))
                .ifPresent( outroEstadio -> {
                    throw new EstadioExisteException("Já existe um estadio com esse nome nesse estado.");
               } );

        estadio.setNome(dto.getNome());
        estadio.setUf(dto.getUf());
        estadio.setCep(dto.getCep());
        estadio.setDataCriacao(dto.getDataCriacao());
        estadio.setStatus(dto.getStatus());

        estadiosRepository.save(estadio);
        return "Estadio atualizado com sucesso!";
    }

    public void inativarEstadio(Long id) {
        Estadio estadio = estadiosRepository.findById(id)
                .orElseThrow(() -> new EstadioNaoEncontradoException("Estadio não encontrado!"));

        if (!estadio.getStatus()) {
            throw new RuntimeException("Estadio está inativo.");
        }

        if (!Boolean.FALSE.equals(estadio.getStatus())) {
            estadio.setStatus(false);
            estadiosRepository.save(estadio);
        }
    }

    public Estadio buscarEstadioPorId(Long id) {
        return estadiosRepository.findById(id).orElseThrow(() ->
                new EstadioNaoEncontradoException("Estadio não encontrado!"));
    }

    public Page<Estadio> listarEstadio(String nome, String uf, Boolean status, Pageable pageable) {
        Specification<Estadio> spec = EstadioSpecifications.nomeContem(nome)
                .and(EstadioSpecifications.ufIgual(uf))
                .and(EstadioSpecifications.statusIgual(status));
        return estadiosRepository.findAll(spec, pageable);
    }

    public Object listarEstadio(String nome, String uf, boolean equials, Pageable any) {
        return null;
    }
}
