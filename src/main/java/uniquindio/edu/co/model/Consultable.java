package uniquindio.edu.co.model;

import java.util.List;

public interface Consultable {
    List<Pelicula> consultarPeliculas();
    List<Funcion> consultarFunciones();
    List<Asiento> consultarDisponibilidad(String codFuncion);
    List<Funcion> consultarFuncionesPorPelicula(String codigoPelicula);
    List<Venta> consultarVentasPorCliente(String identificacionCliente);
    int consultarAsientosDisponiblesEnFuncion(String codigoFuncion);
}
