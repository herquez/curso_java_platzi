package platzi.play.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import platzi.play.contenido.Genero;
import platzi.play.contenido.Pelicula;

public class FileUtils {
    public static final String FILE_PATH = "contenido.txt";
    public static final String SPLITTER = "\\|";

    public static List<Pelicula> leerContenido() {
        List<Pelicula> peliculas = new ArrayList<>();

        try {
            List<String> lineas = Files.readAllLines(Paths.get(FILE_PATH));
            lineas.forEach(linea -> {
                String[] partes = linea.split(SPLITTER);
                String titulo = partes[0].trim();
                int duracion = Integer.parseInt(partes[1].trim());
                Genero genero = Genero.valueOf(partes[2].trim().toUpperCase());
                double calificacion = partes[3].isBlank() ? 0.0 : Double.parseDouble(partes[3]);
                LocalDate fechaEstreno = LocalDate.parse(partes[4].trim());

                peliculas.add(new Pelicula(titulo, duracion, genero, calificacion, fechaEstreno));
            });
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de contenido: " + e.getMessage());
        }
        return peliculas;
    }
}
