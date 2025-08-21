package campeonato.com.Campeonato.application.usecases;

import campeonato.com.Campeonato.domain.entities.Clube;
import campeonato.com.Campeonato.domain.exception.ClubeExisteException;
import campeonato.com.Campeonato.domain.exception.ClubeNaoEncontradoException;
import campeonato.com.Campeonato.domain.ports.inbound.ClubeUseCasePort;
import campeonato.com.Campeonato.domain.ports.outbound.ClubeRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public class ClubeUseCaseImpl implements ClubeUseCasePort {

    private final ClubeRepositoryPort clubeRepositoryPort;

    public ClubeUseCaseImpl(ClubeRepositoryPort clubeRepositoryPort) {
        this.clubeRepositoryPort = clubeRepositoryPort;
    }

    @Override
    public Clube criarClube(String nome, String uf, LocalDate dataCriacao) {

        if (clubeRepositoryPort.existePorNomeEUf(nome, uf)) {
            throw new ClubeExisteException("Já existe um clube com esse nome nesse estado.");
        }

        Clube novoClube = new Clube(nome, uf, dataCriacao);

        return clubeRepositoryPort.salvar(novoClube);
    }

    @Override
    public Clube atualizarClube(Long id, String nome, String uf, LocalDate dataCriacao) {

        Clube clubeExistente = clubeRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new ClubeNaoEncontradoException("Clube não encontrado!"));

        if (clubeRepositoryPort.existePorNomeEUfExcluindoId(nome, uf, id)) {
            throw new ClubeExisteException("Já existe um clube com esse nome nesse estado.");
        }

        Clube clubeAtualizado = clubeExistente.atualizar(nome, uf, dataCriacao);

        return clubeRepositoryPort.salvar(clubeAtualizado);
    }

    @Override
    public void inativarClube(Long id) {

        Clube clube = clubeRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new ClubeNaoEncontradoException("Clube não encontrado!"));

        if (clube.isAtivo()) {
            Clube clubeInativo = clube.inativar();
            clubeRepositoryPort.salvar(clubeInativo);
        }
    }

    @Override
    public Clube buscarClubePorId(Long id) {
        return clubeRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new ClubeNaoEncontradoException("Clube não encontrado!"));
    }

    @Override
    public Page<Clube> listarClubes(String nome, String uf, Boolean ativo, Pageable pageable) {
        return clubeRepositoryPort.listar(nome, uf, ativo, pageable);
    }
}