package campeonato.com.Campeonato.domain.exception;

public class ClubeExisteException extends RuntimeException {
    public ClubeExisteException(String message) {
        super(message);
    }
}