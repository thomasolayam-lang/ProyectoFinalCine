package uniquindio.edu.co.model;

import java.util.List;

public interface Consultable {
    List<Funcion> consultarFuncionesPorPelicula(String codigoPelicula);
    List<Venta> consultarVentasPorCliente(String cedulaCliente);
    int consultarAsientosDisponiblesEnFuncion(String codigoFuncion);
}
