package campeonato.com.Campeonato.services;

import campeonato.com.Campeonato.dto.PartidaRequestDto;
import campeonato.com.Campeonato.exception.PartidaExisteException;
import campeonato.com.Campeonato.exception.PartidaNaoEncontradaException;
import campeonato.com.Campeonato.model.Clube;
import campeonato.com.Campeonato.model.Partida;
import campeonato.com.Campeonato.repository.ClubeRepository;
import campeonato.com.Campeonato.repository.PartidaRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
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
                .filter(outraPartida -> !outraPartida.getId().equals(id))
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
    public Page<Partida> listarPartidas(
            String clube1,
            String clube2,
            String estadio,
            LocalDate dataHorario,
            Boolean status,
            Pageable pageable
    ) {
        List<Partida> partidas = partidaRepository.findAll();

        if (clube1 != null) {
            partidas = partidas.stream()
                    .filter(p -> p.getClube1Id() != null && p.getClube1Id().toLowerCase().contains(clube1.toLowerCase()))
                    .toList();
        }

        if (clube2 != null) {
            partidas = partidas.stream()
                    .filter(p -> p.getClube2Id() != null && p.getClube2Id().toLowerCase().contains(clube2.toLowerCase()))
                    .toList();
        }

        if (estadio != null) {
            partidas = partidas.stream()
                    .filter(p -> p.getEstadio() != null && p.getEstadio().toLowerCase().contains(estadio.toLowerCase()))
                    .toList();
        }

        if (dataHorario != null) {
            partidas = partidas.stream()
                    .filter(p -> dataHorario.equals(p.getDataHorario().toLocalDate()))
                    .toList();
        }

        if (status != null) {
            partidas = partidas.stream()
                    .filter(p -> p.getStatus() != null && p.getStatus().equals(status))
                    .toList();
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), partidas.size());
        List<Partida> pageContent = (start >= partidas.size()) ? List.of() : partidas.subList(start, end);

        return new PageImpl<>(pageContent, pageable, partidas.size());
    }

    public ClubeRepository getClubeRepository() {
        return clubeRepository;
    }

    public void setClubeRepository (ClubeRepository clubeRepository) {
        this.clubeRepository = clubeRepository;
    }
}