package campeonato.com.Campeonato.domain.exception;

public class PartidaExisteException extends RuntimeException {
  public PartidaExisteException(String msg) {
    super(msg);
  }
}