package platzi.play.contenido;

public class Documental extends Contenido {
    String narrador;

    public Documental(String titulo, int duracion, Genero genero) {
        super(titulo, duracion, genero);
    }

    public Documental(String titulo, int duracion, Genero genero, double calificacion) {
        super(titulo, duracion, genero, calificacion);
    }

    public Documental(String titulo, int duracion, Genero genero, double calificacion, java.time.LocalDate fechaEstreno) {
        super(titulo, duracion, genero, calificacion, fechaEstreno);
    }
    
    public Documental(String titulo, int duracion, Genero genero, String narrador) {
        super(titulo, duracion, genero);
        this.narrador = narrador;
    }
    
    public Documental(String titulo, int duracion, Genero genero, double calificacion, String narrador) {
        super(titulo, duracion, genero, calificacion);
        this.narrador = narrador;
    }
    
    public Documental(String titulo, int duracion, Genero genero, double calificacion, java.time.LocalDate fechaEstreno, String narrador) {
        super(titulo, duracion, genero, calificacion, fechaEstreno);
        this.narrador = narrador;
    }

    public String getNarrador() {
        return narrador;
    }
}
