package campeonato.com.Campeonato.domain.exception;

public class PartidaNaoEncontradaException extends RuntimeException {
    public PartidaNaoEncontradaException(String msg) {
        super(msg);
    }
}