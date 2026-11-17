package uniquindio.edu.co.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    Cliente cliente = new Cliente("1094123", "Juan Pérez", TipoCliente.ESTUDIANTE);

    @Test
    void clienteTengaDatosCorrectos() {
        assertEquals("1094123", cliente.getIdentificacion());
        assertEquals("Juan Pérez", cliente.getNombre());
        assertEquals(TipoCliente.ESTUDIANTE, cliente.getTipoCliente());
    }

    @Test
    void modificarNombreClienteExitosamente() {
        cliente.setNombre("Juan Andrés");
        assertEquals("Juan Andrés", cliente.getNombre());
    }

    @Test
    void modificarIdentificacionClienteExitosamente() {
        cliente.setIdentificacion("9999999");
        assertEquals("9999999", cliente.getIdentificacion());
    }

    @Test
    void cambiarTipoClienteExitosamente() {
        cliente.setTipoCliente(TipoCliente.ADULTO_MAYOR);
        assertEquals(TipoCliente.ADULTO_MAYOR, cliente.getTipoCliente());
    }
}