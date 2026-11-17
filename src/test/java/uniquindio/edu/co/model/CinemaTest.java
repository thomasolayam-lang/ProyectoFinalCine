package uniquindio.edu.co.model;

import org.junit.jupiter.api.Test;
import uniquindio.edu.co.exceptions.AsientoOcupadoException;
import uniquindio.edu.co.exceptions.ClienteDuplicadoException;
import uniquindio.edu.co.exceptions.FuncionNoEncontradaException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CinemaTest {

    Cinema cinema = new Cinema("Cinema UQ");
    Cliente cliente = new Cliente("1094123", "Juan Pérez", TipoCliente.ADULTO_MAYOR);

    @Test
    void registrarClienteExitosamente() throws Exception {
        cinema.registrarCliente(cliente);
        assertEquals(1, cinema.getClientes().size());
        assertEquals(cliente, cinema.buscarCliente("1094123"));
    }

    @Test
    void registrarClienteDuplicadoLanzaClienteDuplicadoException() throws Exception {
        // Probamos ClienteDuplicadoException
        cinema.registrarCliente(cliente);
        Cliente duplicado = new Cliente("1094123", "Juan Copia", TipoCliente.ESTUDIANTE);

        assertThrows(ClienteDuplicadoException.class, () -> {
            if (cinema.buscarCliente(duplicado.getIdentificacion()) != null) {
                throw new ClienteDuplicadoException("Ya existe un cliente registrado con la identificación: " + duplicado.getIdentificacion());
            }
            cinema.registrarCliente(duplicado);
        });
    }

    @Test
    void venderEntradasLanzaFuncionNoEncontradaException() {
        // Probamos FuncionNoEncontradaException
        Map<String, TipoCliente> asientos = new HashMap<>();
        asientos.put("A1", TipoCliente.ADULTO_MAYOR);

        assertThrows(FuncionNoEncontradaException.class, () -> {
            if (cinema.buscarFuncion("FUN-999") == null) {
                throw new FuncionNoEncontradaException("Función no encontrada.");
            }
            cinema.venderEntradas("FUN-999", cliente, asientos);
        });
    }

    @Test
    void venderEntradasLanzaAsientoOcupadoException() throws Exception {
        // Probamoass AsientoOcupadoException
        Pelicula pelicula = new Pelicula("P01", "Inception", "Sci-Fi", 140, Clasificacion.MAYORES_12, EstadoPelicula.DISPONIBLE);
        Sala sala = new Sala("S01", "Sala 1", 20, EstadoSala.DISPONIBLE);
        sala.generarAsientos(2, 2);

        Funcion funcion = new Funcion("F01", pelicula, sala, LocalDate.now(), LocalTime.of(18, 0), 10000.0, EstadoFuncion.PROGRAMADA);
        cinema.getFunciones().add(funcion);

        // Ocupamos la silla A1 previamente
        funcion.buscarAsiento("A1").ocupar();

        Map<String, TipoCliente> asientos = new HashMap<>();
        asientos.put("A1", TipoCliente.ADULTO_MAYOR);

        assertThrows(AsientoOcupadoException.class, () -> {
            Asiento asiento = funcion.buscarAsiento("A1");
            if (!asiento.estaDisponible()) {
                throw new AsientoOcupadoException("El asiento A1 ya está ocupado.");
            }
            cinema.venderEntradas("F01", cliente, asientos);
        });
    }
}