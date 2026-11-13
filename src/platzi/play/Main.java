package platzi.play;

import platzi.play.contenido.Pelicula;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import platzi.play.contenido.Genero;
import platzi.play.plataforma.Plataforma;
import platzi.play.util.ScannerUtils;

public class Main {
    public static final String NOMBRE_PLATAFORMA = "PLATZI PLAY";
    public static final String VERSION = "1.0.0";

    public static final int AGREGAR = 1;
    public static final int MOSTRAR_TODO = 2;
    public static final int BUSCAR_POR_TITULO = 3;
    public static final int BUSCAR_POR_GENERO = 4;
    public static final int VER_POPULARES = 5;
    public static final int VER_N_POPULARES = 6;
    public static final int BUSCAR_PELICULA_MAS_LARGA = 7;
    public static final int BUSCAR_PELICULA_MAS_CORTA = 8;
    public static final int REPRODUCIR = 9;
    public static final int ELIMINAR = 10;
    public static final int SALIR = 11;

    public static void main(String[] args) {
        Plataforma plataforma = new Plataforma(NOMBRE_PLATAFORMA);
        System.out.println(NOMBRE_PLATAFORMA + " v" + VERSION);

        cargarPeliculas(plataforma);
        System.out.println("Duración total de contenido: " + plataforma.getDuracionTotal() + " minutos\n");

        while (true) {
            int opcionElegida = ScannerUtils.capturarNumero("""
                    Ingrese una de las siguientes opciones:
                    1. Agregar contenido
                    2. Mostrar todo
                    3. Buscar por titulo
                    4. Buscar por genero
                    5. Ver mas populares
                    6. Ver N mas populares
                    7. Buscar pelicula mas larga
                    8. Buscar pelicula mas corta
                    9. Reproducir contenido
                    10. Eliminar
                    11. Salir
                    """);

            switch (opcionElegida) {
                case AGREGAR -> {
                    String nombre = ScannerUtils.capturarTexto("Nombre del contenido");
                    try {
                        Genero.valueOf(ScannerUtils.capturarTexto("Genero del contenido"));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Genero no valido. Los generos validos son: ");
                        for (Genero genero : Genero.values()) {
                            System.out.println("- " + genero);
                        }
                        break;
                    }
                    Genero genero = Genero.valueOf(ScannerUtils.capturarTexto("Genero del contenido"));
                    int duracion = ScannerUtils.capturarNumero("Duracion del contenido");
                    double calificacion = ScannerUtils.capturarDecimal("Calificacion del contenido");

                    try {
                        plataforma.agregar(new Pelicula(nombre, duracion, genero, calificacion));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case MOSTRAR_TODO -> plataforma.getContenido().forEach(System.out::println);
                case BUSCAR_POR_TITULO -> {
                    String nombreBuscado = ScannerUtils.capturarTexto("Nombre del contenido a buscar");
                    Pelicula contenido = plataforma.buscarPorTitulo(nombreBuscado);

                    if (contenido != null) {
                        System.out.println(contenido.obtenerFichaTecnica());
                    } else {
                        System.out.println(nombreBuscado + " no existe dentro de " + plataforma.getNombre());
                    }
                }
                case BUSCAR_POR_GENERO -> {
                    System.out.println("Generos disponibles: ");
                    for (Genero genero : Genero.values()) {
                        System.out.println("- " + genero);
                    }

                    Genero generoBuscado;
                    try {
                        generoBuscado = Genero.valueOf(ScannerUtils.capturarTexto("Genero del contenido a buscar:"));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Genero no valido. Los generos validos son: ");
                        for (Genero genero : Genero.values()) {
                            System.out.println("- " + genero);
                        }
                        break;
                    }

                    var contenidos = plataforma.buscarPorGenero(generoBuscado);

                    if (!contenidos.isEmpty()) {
                        contenidos.forEach(System.out::println);
                    } else {
                        System.out.println("No hay contenido de genero " + generoBuscado + " dentro de " + plataforma.getNombre());
                    }
                }
                case VER_POPULARES -> {
                    var populares = plataforma.getPopulares();

                    if (!populares.isEmpty()) {
                        populares.forEach(System.out::println);
                    } else {
                        System.out.println("No hay contenido popular dentro de " + plataforma.getNombre());
                    }
                }
                case VER_N_POPULARES -> {
                    int n = ScannerUtils.capturarNumero("Ingrese el numero de las mas populares a mostrar");
                    var populares = plataforma.getPopulares(n);

                    if (!populares.isEmpty()) {
                        populares.forEach(System.out::println);
                    } else {
                        System.out.println("No hay contenido popular dentro de " + plataforma.getNombre());
                    }
                }
                case BUSCAR_PELICULA_MAS_LARGA -> {
                    Pelicula peliculaMasLarga = plataforma.getPeliculaMasLarga();

                    if (peliculaMasLarga != null) {
                        System.out.println("La pelicula mas larga es: " + peliculaMasLarga.getTitulo() + " con una duracion de " + peliculaMasLarga.getDuracion() + " minutos");
                    } else {
                        System.out.println("No hay contenido dentro de " + plataforma.getNombre());
                    }
                }
                case BUSCAR_PELICULA_MAS_CORTA ->
                {
                    Pelicula peliculaMasCorta = plataforma.getPeliculaMasCorta();

                    if (peliculaMasCorta != null) {
                        System.out.println("La pelicula mas corta es: " + peliculaMasCorta.getTitulo() + " con una duracion de " + peliculaMasCorta.getDuracion() + " minutos");
                    } else {
                        System.out.println("No hay contenido dentro de " + plataforma.getNombre());
                    }
                }
                case REPRODUCIR -> {
                    String nombreAReproducir = ScannerUtils.capturarTexto("Nombre del contenido a reproducir");
                    Pelicula contenido = plataforma.buscarPorTitulo(nombreAReproducir);

                    if (contenido != null) {
                        plataforma.reproducir(contenido);
                    } else {
                        System.out.println(nombreAReproducir + " no existe dentro de " + plataforma.getNombre());
                    }
                }
                case ELIMINAR -> {
                    String nombreAEliminar = ScannerUtils.capturarTexto("Nombre del contenido a eliminar");
                    Pelicula contenido = plataforma.buscarPorTitulo(nombreAEliminar);

                    if (contenido != null) {
                        plataforma.eliminar(contenido);
                        System.out.println(nombreAEliminar + " eliminado! ❌");
                    } else {
                        System.out.println(nombreAEliminar + " no existe dentro de " + plataforma.getNombre());
                    }
                }
                case SALIR -> System.exit(0);
            }
        }
    }

    private static void cargarPeliculas(Plataforma plataforma) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get("contenido.txt"));
            lineas.forEach(linea -> {
                String[] partes = linea.split("|");
                String titulo = partes[0].trim();
                int duracion = Integer.parseInt(partes[1].trim());
                Genero genero = Genero.valueOf(partes[2].trim().toUpperCase());
                double calificacion = partes[3].isBlank() ? 0.0 : Double.parseDouble(partes[3]);
                LocalDate fechaEstreno = LocalDate.parse(partes[4].trim());

                try {
                    plataforma.agregar(new Pelicula(titulo, duracion, genero, calificacion, fechaEstreno));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de contenido: " + e.getMessage());
        }
    }
}
