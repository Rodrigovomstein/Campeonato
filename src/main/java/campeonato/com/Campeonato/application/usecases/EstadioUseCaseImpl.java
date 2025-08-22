package campeonato.com.Campeonato.application.usecases;

import campeonato.com.Campeonato.domain.ports.inbound.EstadioUseCasePort;
import campeonato.com.Campeonato.domain.ports.outbound.EstadioRepositoryPort;
import campeonato.com.Campeonato.domain.ports.outbound.ViaCepServicePort;
import campeonato.com.Campeonato.adapters.inbound.dto.ViaCepDto;
import campeonato.com.Campeonato.domain.exception.EstadioExisteException;
import campeonato.com.Campeonato.domain.exception.EstadioNaoEncontradoException;
import campeonato.com.Campeonato.domain.entities.Estadio;
import campeonato.com.Campeonato.domain.valueobjects.Endereco;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


public class EstadioUseCaseImpl implements EstadioUseCasePort {

    private final EstadioRepositoryPort estadioRepositoryPort;
    private final ViaCepServicePort viaCepServicePort;

    public EstadioUseCaseImpl(EstadioRepositoryPort estadioRepositoryPort,
                              ViaCepServicePort viaCepServicePort) {
        this.estadioRepositoryPort = estadioRepositoryPort;
        this.viaCepServicePort = viaCepServicePort;
    }

    @Override
    public Estadio criarEstadio(String nome, String cep, String uf) {
        validarEstadioJaExiste(nome, uf);

        Endereco endereco = validarEBuscarEndereco(cep, uf);

        Estadio estadio = new Estadio(nome, endereco);

        return estadioRepositoryPort.salvar(estadio);
    }

    @Override
    public Estadio atualizarEstadio(Long id, String nome, String cep, String uf) {
        Estadio estadioExistente = estadioRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new EstadioNaoEncontradoException("Estadio não encontrado!"));

        validarEstadioJaExisteParaAtualizacao(nome, uf, id);

        Endereco novoEndereco = validarEBuscarEndereco(cep, uf);

        Estadio estadioAtualizado = new Estadio(
                nome,
                novoEndereco
        );
        return estadioRepositoryPort.salvar(estadioAtualizado);
    }
    @Override
    public void inativarEstadio(Long id) {
        Estadio estadio = estadioRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new EstadioNaoEncontradoException("Estadio não encontrado!"));

        if (!estadio.getAtivo()) {
            throw new RuntimeException("Estadio já está inativo.");
        }

        Estadio estadioInativo = new Estadio(
                estadio.getNome(),
                estadio.getEndereco()
        );
        estadioRepositoryPort.salvar(estadioInativo);
    }

    @Override
    public Estadio buscarEstadioPorId(Long id) {
        return estadioRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new EstadioNaoEncontradoException("Estadio não encontrado!"));
    }

    @Override
    public Page<Estadio> listarEstadio(String nome, String uf, Boolean ativo, Pageable pageable) {
        return estadioRepositoryPort.listar(nome, uf, ativo, pageable);
    }

    private void validarEstadioJaExiste(String nome, String uf) {
        boolean jaExiste = estadioRepositoryPort.existePorNomeEUf(nome, uf);

        if (jaExiste) {
            throw new EstadioExisteException("Já existe um estadio com esse nome nesse estado.");
        }
    }

    private void validarEstadioJaExisteParaAtualizacao(String nome, String uf, Long idExcluir) {
        estadioRepositoryPort.buscarPorNomeEUf(nome, uf)
                .filter(outroEstadio -> !outroEstadio.getId().equals(idExcluir))
                .ifPresent(outroEstadio -> {
                    throw new EstadioExisteException("Já existe um estadio com esse nome nesse estado.");
                });
    }
    private Endereco validarEBuscarEndereco(String cep, String uf) {
        try {
            ViaCepDto viaCepDto = viaCepServicePort.buscarEnderecoPorCep(cep);

            if (!viaCepDto.getUf().equalsIgnoreCase(uf)) {
                throw new RuntimeException("CEP " + cep + " não pertence ao estado " + uf +
                        ". CEP pertence ao estado " + viaCepDto.getUf());
            }

            return new Endereco(
                    viaCepDto.getCep(),
                    viaCepDto.getLogradouro(),
                    viaCepDto.getBairro(),
                    viaCepDto.getLocalidade(),
                    viaCepDto.getUf()
            );

        }
        catch (RuntimeException e) {
            return new Endereco(cep, uf);
        }
    }
}