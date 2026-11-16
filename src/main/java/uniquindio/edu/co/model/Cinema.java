package uniquindio.edu.co.model;

import java.util.ArrayList;
import java.util.List;

public class Cinema implements Buscable, Consultable {

    private String nombre;

    //Listas
    private List<Pelicula> peliculas;
    private List<Sala> salas;
    private List<Funcion> funciones;
    private List<Cliente> clientes;
    private List<Venta> ventas;

    public Cinema(String nombre) {
        this.nombre = nombre;
      //incializamos las listas
        this.peliculas = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.funciones = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }

    public List<Funcion> getFunciones() {
        return funciones;
    }

    public void setFunciones(List<Funcion> funciones) {
        this.funciones = funciones;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    @Override
    public Object buscarPorCodigo(String codigo) {
        return null;
    }

    @Override
    public List<Funcion> consultarFuncionesPorPelicula(String codigoPelicula) {
        return List.of();
    }

    @Override
    public List<Venta> consultarVentasPorCliente(String cedulaCliente) {
        return List.of();
    }

    @Override
    public int consultarAsientosDisponiblesEnFuncion(String codigoFuncion) {
        return 0;
    }
}
