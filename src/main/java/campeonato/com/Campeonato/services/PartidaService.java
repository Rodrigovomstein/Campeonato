package campeonato.com.Campeonato.services;

import campeonato.com.Campeonato.Specifications.PartidaSpecifications;
import campeonato.com.Campeonato.dto.PartidaRequestDto;
import campeonato.com.Campeonato.exception.PartidaExisteException;
import campeonato.com.Campeonato.exception.PartidaNaoEncontradaException;
import campeonato.com.Campeonato.model.Clube;
import campeonato.com.Campeonato.model.Partida;
import campeonato.com.Campeonato.repository.ClubeRepository;
import campeonato.com.Campeonato.repository.PartidaRepository;
import java.time.LocalDateTime;

import io.micrometer.common.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ClubeRepository clubeRepository;

    public String cadastrarPartida(PartidaRequestDto partidaRequestDto) {
        boolean jaExiste = partidaRepository
                .findByEstadioAndUfIgnoreCase(partidaRequestDto.getEstadio(), partidaRequestDto.getUf())
                .isPresent();

        if (partidaRequestDto.getGolsClube1() < 0) {
            throw new RuntimeException("Gols do clube 1 não podem ser negativos.");
        }
        if (partidaRequestDto.getGolsClube2() < 0) {
            throw new RuntimeException("Gols do clube 2 não podem ser negativos.");
        }
        if (partidaRequestDto.getDataHorario().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Data da partida não pode ser no futuro.");
        }
        if (jaExiste) {
            throw new PartidaExisteException("Já existe essa partida.");
        }

        Clube clube1 = clubeRepository.findById(partidaRequestDto.getClube1Id())
                .orElseThrow(() -> new RuntimeException("Clube 1 não encontrado!"));
        Clube clube2 = clubeRepository.findById(partidaRequestDto.getClube2Id())
                .orElseThrow(() -> new RuntimeException("Clube 2 não encontrado!"));

        Partida partida = new Partida();
        partida.setEstadio(partidaRequestDto.getEstadio());
        partida.setUf(partidaRequestDto.getUf());
        partida.setDataHorario(partidaRequestDto.getDataHorario());
        partida.setStatus(partidaRequestDto.getStatus());
        partida.setClube1Id(String.valueOf(clube1));
        partida.setClube2Id(String.valueOf(clube2));

        partidaRepository.save(partida);
        return "Partida " + partida.getEstadio() + " cadastrada com sucesso!";
    }

    public String atualizarPartida(Long id, PartidaRequestDto dto) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new PartidaNaoEncontradaException("Partida não encontrada!"));

        partidaRepository.findByEstadioAndUfIgnoreCase(dto.getEstadio(), dto.getUf())
                .filter(outraPartida -> !outraPartida.getClass().equals(id))
                .ifPresent(outraPartida -> {
                    throw new PartidaExisteException("Já existe essa partida.");
                });

        partida.setEstadio(dto.getEstadio());
        partida.setUf(dto.getUf());
        partida.setDataHorario(dto.getDataHorario());
        partida.setStatus(dto.getStatus());

        partidaRepository.save(partida);
        return "Partida atualizada com sucesso!";
    }

    public void inativarPartida(Long id) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new PartidaNaoEncontradaException("Partida não encontrada!"));

        if (Boolean.TRUE.equals(partida.getStatus())) {
            partida.setStatus(false);
            partidaRepository.save(partida);
        }
    }

    public Partida buscarPartidaPorId(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new PartidaNaoEncontradaException("Partida não encontrada!"));
    }
    public Page<Partida> listarPartida(String estadio, String uf, LocalDateTime dataHorario, Boolean status, String clube1Id, String clube2Id, Pageable pageable) {
        Specification<Partida> spec = PartidaSpecifications.estadioContem(estadio)
                .and(PartidaSpecifications.clube1Igual(clube1Id))
                .and(PartidaSpecifications.clube2Igual(clube2Id))
                .and(PartidaSpecifications.dataHorarioIgual(dataHorario))
                .and(PartidaSpecifications.ufIgual(uf))
                .and(PartidaSpecifications.statusIgual(status));
        return partidaRepository.findAll(spec, pageable);
    }

    public ClubeRepository getClubeRepository() {
        return clubeRepository;
    }

    public void setClubeRepository (ClubeRepository clubeRepository) {
        this.clubeRepository = clubeRepository;
    }
}