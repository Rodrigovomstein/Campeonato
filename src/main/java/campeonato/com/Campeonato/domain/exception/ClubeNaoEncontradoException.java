package campeonato.com.Campeonato.domain.exception;

public class ClubeNaoEncontradoException extends RuntimeException {
    public ClubeNaoEncontradoException(String msg) {
        super(msg);
    }
}