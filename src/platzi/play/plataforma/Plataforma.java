package platzi.play.plataforma;

import platzi.play.contenido.Genero;
import platzi.play.contenido.Pelicula;
import platzi.play.contenido.ResumenContenido;
import platzi.play.exceptions.PeliculaExistenteException;

import java.util.ArrayList;
import java.util.List;

public class Plataforma {
    private String nombre;
    private List<Pelicula> contenido; // Agregación

    public Plataforma(String nombre) {
        this.nombre = nombre;
        this.contenido = new ArrayList<>();
    }

    public void agregar(Pelicula elemento) throws PeliculaExistenteException {
        if (buscarPorTitulo(elemento.getTitulo()) != null) {
            throw new PeliculaExistenteException(elemento.getTitulo());
        }
        this.contenido.add(elemento);
    }

    public List<String> getTitulos() {
        return contenido.stream()
                .map(Pelicula::getTitulo)
                .toList();
    }

    public void eliminar(Pelicula elemento) {
        this.contenido.remove(elemento);
    }

    public Pelicula buscarPorTitulo(String titulo) {
        return contenido.stream()
                .filter(pelicula -> pelicula.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
    }

    public List<Pelicula> buscarPorGenero(Genero genero) {
        return contenido.stream()
                .filter(pelicula -> pelicula.getGenero().equals(genero))
                .toList();
    }

    public String getNombre() {
        return nombre;
    }

    public List<ResumenContenido> getContenido() {
        return contenido.stream()
                .map(p -> new ResumenContenido(p.getTitulo(), p.getDuracion(), p.getGenero()))
                .toList();
    }

    public int getDuracionTotal() {
        return contenido.stream().mapToInt(Pelicula::getDuracion).sum();
    }

    public List<Pelicula> getPopulares() {
        return contenido.stream()
                .filter(Pelicula::esPopular)
                .sorted((p1, p2) -> Double.compare(p2.getCalificacion(), p1.getCalificacion()))
                .toList();
    }

    public List<Pelicula> getPopulares(int cantidad) {
        return contenido.stream().sorted((p1, p2) -> Double.compare(p2.getCalificacion(), p1.getCalificacion()))
                .limit(cantidad)
                .toList();
    }

    public Pelicula getPeliculaMasLarga() {
        return contenido.stream()
                .max((p1, p2) -> Integer.compare(p1.getDuracion(), p2.getDuracion()))
                .orElse(null);
    }

    public Pelicula getPeliculaMasCorta() {
        return contenido.stream()
                .min((p1, p2) -> Integer.compare(p1.getDuracion(), p2.getDuracion()))
                .orElse(null);
    }
}
