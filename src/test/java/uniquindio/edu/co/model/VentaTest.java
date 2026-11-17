package uniquindio.edu.co.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VentaTest {

    Cliente cliente = new Cliente("1094123", "Juan Pérez", TipoCliente.ADULTO_MAYOR);
    Pelicula pelicula = new Pelicula("P01", "Inception", "Sci-Fi", 140, Clasificacion.MAYORES_12, EstadoPelicula.DISPONIBLE);
    Sala sala = new Sala("S01", "Sala 1", 20, EstadoSala.DISPONIBLE);
    Funcion funcion = new Funcion("F01", pelicula, sala, LocalDate.now(), LocalTime.of(18, 0), 10000.0, EstadoFuncion.PROGRAMADA);

    @Test
    void calcularTotalPagadoCorrectamente() {
        Asiento a1 = new Asiento("A1", 'A', 1, EstadoAsiento.DISPONIBLE);
        Asiento a2 = new Asiento("A2", 'A', 2, EstadoAsiento.DISPONIBLE);

        List<DetalleVenta> detalles = new ArrayList<>();
        detalles.add(new DetalleVenta(a1, TipoCliente.ADULTO_MAYOR, funcion.getValorBase()));
        detalles.add(new DetalleVenta(a2, TipoCliente.ESTUDIANTE, funcion.getValorBase()));

        Venta venta = new Venta("VEN-1", cliente, funcion, detalles);

        assertTrue(venta.getValorTotalPagado() > 0);
        assertEquals(2, venta.getCantidadEntradas());
        assertTrue(venta.estaActiva());
    }

    @Test
    void cancelarVentaLiberaAsientosYCambiaEstado() {
        Asiento a1 = new Asiento("A1", 'A', 1, EstadoAsiento.DISPONIBLE);
        a1.ocupar();

        List<DetalleVenta> detalles = new ArrayList<>();
        detalles.add(new DetalleVenta(a1, TipoCliente.ADULTO_MAYOR, funcion.getValorBase()));

        Venta venta = new Venta("VEN-1", cliente, funcion, detalles);
        venta.cancelar();

        assertEquals(EstadoVenta.CANCELADA, venta.getEstado());
        assertFalse(venta.estaActiva());
        assertTrue(a1.estaDisponible());
    }

    @Test
    void ventaIniciaConEstadoActivo() {
        Venta venta = new Venta("VEN-2", cliente, funcion, new ArrayList<>());
        assertTrue(venta.estaActiva());
        assertEquals(0, venta.getCantidadEntradas());
    }

    @Test
    void toStringRetornaInformacionDeLaVenta() {
        Venta venta = new Venta("VEN-3", cliente, funcion, new ArrayList<>());
        assertNotNull(venta.toString());
        assertTrue(venta.toString().contains("VEN-3"));
    }
}