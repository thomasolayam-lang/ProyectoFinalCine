package uniquindio.edu.co.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AsientoTest {

    Asiento asiento = new Asiento("A1", 'A', 1, EstadoAsiento.DISPONIBLE);

    @Test
    void asientoIniciaDisponible() {
        assertTrue(asiento.estaDisponible());
        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstado());
    }

    @Test
    void ocuparAsientoCambiaEstadoAOcupado() {
        asiento.ocupar();
        assertFalse(asiento.estaDisponible());
        assertEquals(EstadoAsiento.OCUPADO, asiento.getEstado());
    }

    @Test
    void liberarAsientoCambiaEstadoADisponible() {
        asiento.ocupar();
        asiento.liberar();
        assertTrue(asiento.estaDisponible());
    }

    @Test
    void verificarDatosCorrectosDelAsiento() {
        assertEquals("A1", asiento.getCodigo());
        assertEquals(Character.valueOf('A'), asiento.getFila());
        assertEquals(1, asiento.getNumero());
    }
}
