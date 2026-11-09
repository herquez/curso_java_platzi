package platzi.play.exceptions;

public class PeliculaExistenteException extends Exception {
    public PeliculaExistenteException(String titulo) {
        super("La película " + titulo + " ya existe en la plataforma.");
    }
    
}
