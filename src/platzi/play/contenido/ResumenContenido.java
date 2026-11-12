package platzi.play.contenido;

public record ResumenContenido(String titulo, int duracion, Genero genero) {
    @Override
    public String toString() {
        return '\n' + titulo +
                "\nDuracion: " + duracion +
                "\nGenero: " + genero;
    }
}
