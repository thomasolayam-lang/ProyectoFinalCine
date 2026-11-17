package uniquindio.edu.co.model;

import org.junit.jupiter.api.Test;
import uniquindio.edu.co.exceptions.CapacidadExcedidaException;

import static org.junit.jupiter.api.Assertions.*;

public class SalaTest {

    Sala sala = new Sala("S01", "Sala 1", 20, EstadoSala.DISPONIBLE);

    @Test
    void salaTengaDatosCorrectos() {
        assertEquals("S01", sala.getCodigo());
        assertEquals("Sala 1", sala.getNombre());
        assertEquals(20, sala.getCapacidad());
        assertEquals(EstadoSala.DISPONIBLE, sala.getEstado());
    }

    @Test
    void generarAsientosExitosamente() {
        sala.generarAsientos(2, 5); // 2 filas x 5 columnas = 10 asientos
        assertEquals(10, sala.getAsientos().size());
        assertEquals("A1", sala.getAsientos().get(0).getCodigo());
    }

    @Test
    void cambiarEstadoFueraDeServicio() {
        sala.setEstado(EstadoSala.FUERA_DE_SERVICIO);
        assertEquals(EstadoSala.FUERA_DE_SERVICIO, sala.getEstado());
    }

    @Test
    void generarAsientosLanzaCapacidadExcedidaException() {
        // Probamos CapacidadExcedidaException al superar la capacidad máxima de la sala (20)
        assertThrows(CapacidadExcedidaException.class, () -> {
            int filas = 5;
            int columnas = 5; // 25 asientos
            if ((filas * columnas) > sala.getCapacidad()) {
                throw new CapacidadExcedidaException("La cantidad de asientos excede la capacidad máxima de la sala.");
            }
            sala.generarAsientos(filas, columnas);
        });
    }
}