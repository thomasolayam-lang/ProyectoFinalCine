package uniquindio.edu.co.model;

import uniquindio.edu.co.model.Pelicula;
import uniquindio.edu.co.model.Funcion;
import uniquindio.edu.co.model.Asiento;
import java.util.List;
public interface Consulatable {

    public interface Consultable {
        List<Pelicula> consultarPeliculas();
        List<Funcion> consultarFunciones();
        List<Asiento> consultarDisponibilidad(String codFuncion);
    }
}
