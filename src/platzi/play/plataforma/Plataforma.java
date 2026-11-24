package platzi.play.plataforma;

import platzi.play.contenido.Genero;
import platzi.play.contenido.Contenido;
import platzi.play.contenido.ResumenContenido;
import platzi.play.exceptions.PeliculaExistenteException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Plataforma {
    private String nombre;
    private List<Contenido> contenido; // Agregación
    private Map<Contenido, Integer> reproducciones;

    public Plataforma(String nombre) {
        this.nombre = nombre;
        this.contenido = new ArrayList<>();
        this.reproducciones = new HashMap<>();
    }

    public void agregar(Contenido elemento) throws PeliculaExistenteException {
        if (buscarPorTitulo(elemento.getTitulo()) != null) {
            throw new PeliculaExistenteException(elemento.getTitulo());
        }
        this.contenido.add(elemento);
    }

    public List<String> getTitulos() {
        return contenido.stream()
                .map(Contenido::getTitulo)
                .toList();
    }

    public void eliminar(Contenido elemento) {
        this.contenido.remove(elemento);
    }

    public Contenido buscarPorTitulo(String titulo) {
        return contenido.stream()
                .filter(pelicula ->
                    pelicula.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
    }

    public List<Contenido> buscarPorGenero(Genero genero) {
        return contenido.stream()
                .filter(pelicula -> pelicula.getGenero().equals(genero))
                .toList();
    }

    public String getNombre() {
        return nombre;
    }

    public List<ResumenContenido> getContenido() {
        return contenido.stream()
                .map(p ->
                    new ResumenContenido(p.getTitulo(),
                    p.getDuracion(), p.getGenero()))
                .toList();
    }

    public int getDuracionTotal() {
        return contenido.stream().mapToInt(Contenido::getDuracion).sum();
    }

    public List<Contenido> getPopulares() {
        return contenido.stream()
                .filter(Contenido::esPopular)
                .sorted((p1, p2) ->
                Double.compare(p2.getCalificacion(), p1.getCalificacion()))
                .toList();
    }

    public List<Contenido> getPopulares(int cantidad) {
        return contenido.stream().sorted((p1, p2) ->
                    Double.compare(p2.getCalificacion(), p1.getCalificacion()))
                .limit(cantidad)
                .toList();
    }

    public Contenido getPeliculaMasLarga() {
        return contenido.stream()
                .max((p1, p2) ->
                    Integer.compare(p1.getDuracion(), p2.getDuracion()))
                .orElse(null);
    }

    public Contenido getPeliculaMasCorta() {
        return contenido.stream()
                .min((p1, p2) ->
                    Integer.compare(p1.getDuracion(), p2.getDuracion()))
                .orElse(null);
    }

    public void reproducir(Contenido pelicula) {
        System.out.println(
            "La pelicula " +
            pelicula.getTitulo() +
            " tiene " +
            reproducciones.getOrDefault(pelicula, 0) +
            " reproducciones.");
        System.out.println("Reproduciendo " + pelicula.getTitulo() + "...");
        incrementarReproducciones(pelicula);
    }

    private void incrementarReproducciones(Contenido pelicula) {
        reproducciones.put(
            pelicula, reproducciones.getOrDefault(pelicula, 0) + 1);
    }
}
