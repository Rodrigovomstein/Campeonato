package campeonato.com.Campeonato.exception;

public class PartidaNaoEncontradaException extends RuntimeException {
    public PartidaNaoEncontradaException(String msg) {
        super(msg);
    }
}