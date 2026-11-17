package uniquindio.edu.co.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DetalleVentaTest {

    Asiento asiento = new Asiento("A1", 'A', 1, EstadoAsiento.DISPONIBLE);
    DetalleVenta detalle = new DetalleVenta(asiento, TipoCliente.ESTUDIANTE, 10000.0);

    @Test
    void detalleVentaTengaDatosCorrectos() {
        assertEquals(asiento, detalle.getAsiento());
        assertEquals(TipoCliente.ESTUDIANTE, detalle.getTipoEntrada());
    }

    @Test
    void calcularValorConTipoCliente() {
        assertTrue(detalle.getValor() > 0);
    }

    @Test
    void obtenerAsientoCorrectoDelDetalle() {
        assertEquals("A1", detalle.getAsiento().getCodigo());
    }

    @Test
    void toStringRetornaTextoValido() {
        assertNotNull(detalle.toString());
        assertTrue(detalle.toString().contains("A1"));
    }
}