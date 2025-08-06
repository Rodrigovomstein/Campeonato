package campeonato.com.Campeonato.services;

import campeonato.com.Campeonato.Specifications.PartidaSpecifications;
import campeonato.com.Campeonato.dto.PartidaRequestDto;
import campeonato.com.Campeonato.exception.PartidaExisteException;
import campeonato.com.Campeonato.exception.PartidaNaoEncontradaException;
import campeonato.com.Campeonato.model.Clube;
import campeonato.com.Campeonato.model.Partida;
import campeonato.com.Campeonato.repository.ClubeRepository;
import campeonato.com.Campeonato.repository.PartidaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public boolean isPartidaValida(PartidaRequestDto dto) {
        if (dto.getGolsClube1() < 0 || dto.getGolsClube2() < 0) return false;
        if (dto.getDataHorario().isAfter(LocalDateTime.now())) return false;
        if (partidaRepository.findByEstadioAndUfIgnoreCase(dto.getEstadio(), dto.getUf()).isPresent()) return false;
        return true;
    }

    public List<PartidaRequestDto> validarPartidas(List<PartidaRequestDto> partidas) {
        return partidas.stream()
                .filter(this::isPartidaValida)
                .collect(Collectors.toList());
    }

    public String cadastrarPartida(PartidaRequestDto partidaRequestDto) {
        if (!isPartidaValida(partidaRequestDto)) {
            throw new RuntimeException("Partida inválida!");
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
        partida.setClube1Id(clube1.getId());
        partida.setClube2Id(clube2.getId());
        partidaRepository.save(partida);
        return "Partida " + partida.getEstadio() + " cadastrada com sucesso!";
    }

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
    public Page<Partida> listarPartidas(String clube1Id, String clube2Id, String uf, String estadio, LocalDate dataHorario, Boolean status, Pageable pageable){
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