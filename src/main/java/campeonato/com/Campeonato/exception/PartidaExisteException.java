package campeonato.com.Campeonato.exception;

public class PartidaExisteException extends RuntimeException {
  public PartidaExisteException(String msg) {
    super(msg);
  }
}