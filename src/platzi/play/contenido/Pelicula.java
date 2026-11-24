package platzi.play.contenido;

public class Pelicula extends Contenido {
    public Pelicula(String titulo, int duracion, Genero genero) {
        super(titulo, duracion, genero);
    }

    public Pelicula(String titulo, int duracion, Genero genero, double calificacion) {
        super(titulo, duracion, genero, calificacion);
    }

    public Pelicula(String titulo, int duracion, Genero genero, double calificacion, java.time.LocalDate fechaEstreno) {
        super(titulo, duracion, genero, calificacion, fechaEstreno);
    }
    
}
