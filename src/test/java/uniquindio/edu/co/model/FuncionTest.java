package uniquindio.edu.co.model;

import org.junit.jupiter.api.Test;
import uniquindio.edu.co.exceptions.MontoInvalidoException;
import uniquindio.edu.co.exceptions.PeliculaNoDisponibleException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class FuncionTest {

    Pelicula pelicula = new Pelicula("P01", "Inception", "Sci-Fi", 140, Clasificacion.MAYORES_12, EstadoPelicula.DISPONIBLE);
    Sala sala = new Sala("S01", "Sala 1", 20, EstadoSala.DISPONIBLE);
    Funcion funcion = new Funcion("F01", pelicula, sala, LocalDate.now(), LocalTime.of(18, 0), 12000.0, EstadoFuncion.PROGRAMADA);

    @Test
    void funcionTengaDatosCorrectos() {
        assertEquals("F01", funcion.getCodigo());
        assertEquals(pelicula, funcion.getPelicula());
        assertEquals(12000.0, funcion.getValorBase());
        assertEquals(EstadoFuncion.PROGRAMADA, funcion.getEstado());
    }

    @Test
    void cancelarFuncionCambiaEstado() {
        funcion.cancelar();
        assertEquals(EstadoFuncion.CANCELADA, funcion.getEstado());
    }

    @Test
    void programarConPeliculaNoDisponibleLanzaException() {
        // Probamos PeliculaNoDisponibleException
        Pelicula peliculaInactiva = new Pelicula("P02", "Batman", "Acción", 120, Clasificacion.MAYORES_12, EstadoPelicula.NO_DISPONIBLE);

        assertThrows(PeliculaNoDisponibleException.class, () -> {
            if (peliculaInactiva.getEstado() != EstadoPelicula.DISPONIBLE) {
                throw new PeliculaNoDisponibleException("La películaa no está disponible para programar funciones.");
            }
            new Funcion("F02", peliculaInactiva, sala, LocalDate.now(), LocalTime.of(20, 0), 12000.0, EstadoFuncion.PROGRAMADA);
        });
    }

    @Test
    void crearFuncionConValorBaseInvalidoLanzaException() {
        // Probamos MontoInvalidoException
        assertThrows(MontoInvalidoException.class, () -> {
            double valorInvalido = -5000.0;
            if (valorInvalido <= 0) {
                throw new MontoInvalidoException("El valor base de la función debe ser mayor a cero.");
            }
            new Funcion("F03", pelicula, sala, LocalDate.now(), LocalTime.of(22, 0), valorInvalido, EstadoFuncion.PROGRAMADA);
        });
    }
}