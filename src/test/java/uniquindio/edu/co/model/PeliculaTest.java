package uniquindio.edu.co.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PeliculaTest {

    Pelicula pelicula = new Pelicula("P01", "Inception", "Sci-Fi", 140, Clasificacion.MAYORES_12, EstadoPelicula.DISPONIBLE);

    @Test
    void peliculaTengaDatosCorrectos() {
        assertEquals("P01", pelicula.getCodigo());
        assertEquals("Inception", pelicula.getNombre());
        assertEquals("Sci-Fi", pelicula.getGenero());
        assertEquals(140, pelicula.getDuracionMin());
        assertEquals(EstadoPelicula.DISPONIBLE, pelicula.getEstado());
    }

    @Test
    void cambiarEstadoPeliculaANoDisponible() {
        pelicula.setEstado(EstadoPelicula.NO_DISPONIBLE);
        assertEquals(EstadoPelicula.NO_DISPONIBLE, pelicula.getEstado());
    }

    @Test
    void modificarNombreYGeneroPelicula() {
        pelicula.setNombre("Interstellar");
        pelicula.setGenero("Ciencia Ficción");
        assertEquals("Interstellar", pelicula.getNombre());
        assertEquals("Ciencia Ficción", pelicula.getGenero());
    }

    @Test
    void verificarClasificacionPelicula() {
        assertEquals(Clasificacion.MAYORES_12, pelicula.getClasificacion());
    }
}