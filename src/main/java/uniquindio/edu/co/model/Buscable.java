package uniquindio.edu.co.model;

public interface Buscable<T> {

    T buscarPorCodigo(String codigo);
}