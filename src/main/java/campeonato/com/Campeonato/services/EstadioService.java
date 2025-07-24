package campeonato.com.Campeonato.services;

import campeonato.com.Campeonato.Specifications.ClubeSpecifications;
import campeonato.com.Campeonato.Specifications.EstadioSpecifications;
import campeonato.com.Campeonato.dto.EstadioRequestDto;
import campeonato.com.Campeonato.exception.EstadioExisteException;
import campeonato.com.Campeonato.exception.EstadioNaoEncontradoException;
import campeonato.com.Campeonato.model.Clube;
import campeonato.com.Campeonato.model.Estadio;
import campeonato.com.Campeonato.repository.EstadioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class EstadioService {

    @Autowired
    private EstadioRepository estadioRepository;

    public String cadastrarEstadio(EstadioRequestDto estadioRequestDto) {
        boolean jaExiste = estadioRepository
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
        estadio.setDataCriacao(estadioRequestDto.getDataCriacao());
        estadio.setStatus(estadioRequestDto.getStatus());

        estadioRepository.save(estadio);

        return "Estadio " + estadio.getNome() + " cadastrado com sucesso!";
    }

    public String atualizarEstadio(Long id, EstadioRequestDto dto) {
        Estadio estadio = estadioRepository.findById(id)
                .orElseThrow(() -> new EstadioNaoEncontradoException("Estadio não encontrado!"));

        estadioRepository.findByNomeAndUfIgnoreCase(dto.getNome(), dto.getUf())
                .filter( outroEstadio -> !outroEstadio.getId().equals(id))
                .ifPresent( outroEstadio -> {
                    throw new EstadioExisteException("Já existe um estadio com esse nome nesse estado.");
               } );

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

        if (!estadio.getStatus()) {
            throw new RuntimeException("Estadio está inativo.");
        }

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
        Specification<Estadio> spec = EstadioSpecifications.nomeContem(nome)
                .and(EstadioSpecifications.ufIgual(uf))
                .and(EstadioSpecifications.statusIgual(status));
        return estadioRepository.findAll(spec, pageable);
    }

    public Object listarEstadios(String flamengo, String rj, boolean eq, Pageable any) {
        return null;
    }
}
