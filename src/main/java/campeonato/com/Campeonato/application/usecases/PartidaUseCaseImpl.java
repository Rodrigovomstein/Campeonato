package campeonato.com.Campeonato.application.usecases;

import campeonato.com.Campeonato.adapters.outbound.repository.JpaClubeRepository;
import campeonato.com.Campeonato.domain.specifications.PartidaSpecifications;
import campeonato.com.Campeonato.adapters.inbound.dto.PartidaRequestDto;
import campeonato.com.Campeonato.domain.exception.PartidaExisteException;
import campeonato.com.Campeonato.domain.exception.PartidaNaoEncontradaException;
import campeonato.com.Campeonato.domain.entities.Partida;
import campeonato.com.Campeonato.domain.ports.PartidaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import campeonato.com.Campeonato.domain.entities.Clube;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class PartidaUseCaseImpl {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private JpaClubeRepository clubeRepository;

    public boolean isPartidaValida(PartidaRequestDto dto) {
        if (dto.getGolsClube1() != null && dto.getGolsClube1() < 0) return false;
        if (dto.getGolsClube2() != null && dto.getGolsClube2() < 0) return false;
        if (dto.getDataHorario() == null || dto.getDataHorario().isBefore(LocalDateTime.now())) return false;
        if (dto.getEstadio() == null || dto.getUf() == null) return false;

        List<Partida> partidas = partidaRepository.findByEstadioIgnoreCaseAndUfIgnoreCase(dto.getEstadio(), dto.getUf());
        if (!partidas.isEmpty()) return false;
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
                .orElseThrow(() -> new RuntimeException("Clube 1 não encontrado!")).toDomain();
        Clube clube2 = clubeRepository.findById(partidaRequestDto.getClube2Id())
                .orElseThrow(() -> new RuntimeException("Clube 2 não encontrado!")).toDomain();

        Partida partida = new Partida();
        partida.setEstadio(partidaRequestDto.getEstadio());
        partida.setUf(partidaRequestDto.getUf());
        partida.setDataHorario(partidaRequestDto.getDataHorario());
        partida.setStatus(partidaRequestDto.getStatus());
        partida.setClubes1Id(clube1.getId());
        partida.setClubes2Id(clube2.getId());
        partidaRepository.save(partida);
        return "Partida " + partida.getEstadio() + " cadastrada com sucesso!";
    }


    public String atualizarPartida(Long id, PartidaRequestDto dto) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new PartidaNaoEncontradaException("Partida não encontrada!"));

        List<Partida> partidas = partidaRepository.findByEstadioIgnoreCaseAndUfIgnoreCase(dto.getEstadio(), dto.getUf());
        if (partidas.stream().anyMatch(p -> !p.getId().equals(id))) {
            throw new PartidaExisteException("Já existe essa partida.");
        }

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

    public JpaClubeRepository getClubeRepository() {
        return clubeRepository;
    }

    public void setClubeRepository (JpaClubeRepository jpaClubeRepository) {
        this.clubeRepository = jpaClubeRepository;
    }

}