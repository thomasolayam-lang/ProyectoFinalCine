package uniquindio.edu.co.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public String toString() {
        return "Cinema{" +
                "nombre='" + nombre + '\'' +
                ", peliculas=" + peliculas +
                ", salas=" + salas +
                ", funciones=" + funciones +
                ", clientes=" + clientes +
                ", ventas=" + ventas +
                '}';
    }

    //METODOS


    //Uso del Interface

    @Override
    public List<Pelicula> consultarPeliculas() {
        List<Pelicula> disponibles = new ArrayList<>();
        for (Pelicula p : peliculas) {
            if (p.getEstado() == EstadoPelicula.DISPONIBLE) {
                disponibles.add(p);
            }
        }
        return disponibles;
    }

    @Override
    public List<Funcion> consultarFunciones() {
        List<Funcion> programadas = new ArrayList<>();
        for (Funcion f : funciones) {
            if (f.getEstado() == EstadoFuncion.PROGRAMADA) {
                programadas.add(f);
            }
        }
        return programadas;
    }

    @Override
    // este metodo nos da una lista de asientos disponibles
    public List<Asiento> consultarDisponibilidad(String codFuncion) {
        Funcion funcion = buscarFuncion(codFuncion);
        if (funcion == null) {
            return new ArrayList<>();
        }
        List<Asiento> disponibles = new ArrayList<>();
        for (Asiento a : funcion.getAsientos()) {
            if (a.estaDisponible()) {
                disponibles.add(a);
            }
        }
        return disponibles;
    }

    @Override
    public List<Funcion> consultarFuncionesPorPelicula(String codigoPelicula) {
        List<Funcion> resultado = new ArrayList<>();
        for (Funcion f : funciones) {
            if (f.getPelicula().getCodigo().equalsIgnoreCase(codigoPelicula)) {
                resultado.add(f);
            }
        }
        return resultado;
    }

    @Override
    public List<Venta> consultarVentasPorCliente(String identificacionCliente) {
        List<Venta> resultado = new ArrayList<>();
        for (Venta v : ventas) {
            if (v.getCliente().getIdentificacion().equalsIgnoreCase(identificacionCliente)) {
                resultado.add(v);
            }
        }
        return resultado;
    }

    @Override
    public int consultarAsientosDisponiblesEnFuncion(String codigoFuncion) {
        Funcion funcion = buscarFuncion(codigoFuncion);
        if (funcion == null) {
            return 0;
        }
        int disponibles = 0;
        for (Asiento a : funcion.getAsientos()) {
            if (a.estaDisponible()) {
                disponibles++;
            }
        }
        return disponibles;
    }

    // Implementación de Buscable

    @Override
    public Object buscarPorCodigo(String codigo) {
        Pelicula p = buscarPelicula(codigo);
        if (p != null) return p;

        Sala s = buscarSala(codigo);
        if (s != null) return s;

        Funcion f = buscarFuncion(codigo);
        if (f != null) return f;

        Venta v = buscarVenta(codigo);
        if (v != null) return v;

        return buscarCliente(codigo);
    }

    // Metodos de Películas
    public void registrarPelicula(Pelicula pelicula) throws Exception {
        if (buscarPelicula(pelicula.getCodigo()) != null) {
            throw new Exception("Ya existe una película con el código: " + pelicula.getCodigo());
        }
        peliculas.add(pelicula);
    }

    public void modificarPelicula(String codigo, Pelicula nuevaPelicula) throws Exception {
        Pelicula existente = buscarPelicula(codigo);
        if (existente == null) {
            throw new Exception("No se encontró la película con código: " + codigo);
        }
        existente.setNombre(nuevaPelicula.getNombre());
        existente.setGenero(nuevaPelicula.getGenero());
        existente.setDuracionMin(nuevaPelicula.getDuracionMin());
        existente.setClasificacion(nuevaPelicula.getClasificacion());
        existente.setEstado(nuevaPelicula.getEstado());
    }

    public void deshabilitarPelicula(String codigo) throws Exception {
        Pelicula pelicula = buscarPelicula(codigo);
        if (pelicula == null) {
            throw new Exception("No se encontró la película con código: " + codigo);
        }
        pelicula.setEstado(EstadoPelicula.NO_DISPONIBLE);
    }

    // Metodos de Salas
    public void registrarSala(Sala sala) throws Exception {
        if (buscarSala(sala.getCodigo()) != null) {
             throw new Exception("Ya existe una sala con el código: " + sala.getCodigo());
        }
        salas.add(sala);
    }

    public void modificarSala(String codigo, Sala nuevaSala) throws Exception {
        Sala existente = buscarSala(codigo);
        if (existente == null) {
            throw new Exception("No se encontró la sala con código: " + codigo);
        }
        existente.setNombre(nuevaSala.getNombre());
        existente.setCapacidad(nuevaSala.getCapacidad());
        existente.setEstado(nuevaSala.getEstado());
    }

    public void deshabilitarSala(String codigo) throws Exception {
        Sala sala = buscarSala(codigo);
        if (sala == null) {
            throw new Exception("No se encontró la sala con código: " + codigo);
        }
        sala.setEstado(EstadoSala.FUERA_DE_SERVICIO);
    }

    // Metodos de Funciones
    public void programarFuncion(Funcion funcion) throws Exception {
        if (buscarFuncion(funcion.getCodigo()) != null) {
            throw new Exception("Ya existe una función con el código: " + funcion.getCodigo());
        }
        if (funcion.getPelicula().getEstado() != EstadoPelicula.DISPONIBLE) {
            throw new Exception("La película no está disponible para programar funciones.");
        }
        if (funcion.getSala().getEstado() != EstadoSala.DISPONIBLE) {
            throw new Exception("La sala se encuentra fuera de servicio.");
        }
        funciones.add(funcion);
    }

    public void cancelarFuncion(String codigo) throws Exception {
        Funcion funcion = buscarFuncion(codigo);
        if (funcion == null) {
            throw new Exception("No se encontró la función con código: " + codigo);
        }
        funcion.setEstado(EstadoFuncion.CANCELADA);
    }



    // Metodos de Ventas
    public Venta venderEntradas(String codigoFuncion, Cliente cliente, Map<String, TipoCliente> asientosYTipos) throws Exception {
        Funcion funcion = buscarFuncion(codigoFuncion);
        if (funcion == null) {
            throw new Exception("Función no encontrada.");
        }
        if (funcion.getEstado() != EstadoFuncion.PROGRAMADA) {
            throw new Exception("La función no está programada para venta.");
        }
        if (asientosYTipos == null || asientosYTipos.isEmpty()) {
            throw new Exception("Debe seleccionar al menos un asiento.");
        }

        List<DetalleVenta> detalles = new ArrayList<>();

        // Valida primero que TODOS los asientos existan y estén libres antes de ocupar
        for (Map.Entry<String, TipoCliente> entrada : asientosYTipos.entrySet()) {
            String codAsiento = entrada.getKey();
            TipoCliente tipo = entrada.getValue();

            Asiento asiento = funcion.buscarAsiento(codAsiento);
            if (asiento == null) {
                throw new Exception("El asiento " + codAsiento + " no existe en esta función.");
            }
            if (!asiento.estaDisponible()) {
                throw new Exception("El asiento " + codAsiento + " ya está ocupado. Venta abortada.");
            }
            detalles.add(new DetalleVenta(asiento, tipo, funcion.getValorBase()));
        }

        // Si todos son válidos, ocuparlos todos
        for (DetalleVenta d : detalles) {
            d.getAsiento().ocupar();
        }

        if (buscarCliente(cliente.getIdentificacion()) == null) {
            clientes.add(cliente);
        }

        String codigoVenta = "V-" + (ventas.size() + 1);
        Venta venta = new Venta(codigoVenta, cliente, funcion, detalles);
        ventas.add(venta);
        return venta;
    }

    public void cancelarVenta(String codigoVenta) throws Exception {
        Venta venta = buscarVenta(codigoVenta);
        if (venta == null) {
            throw new Exception("Venta no encontrada.");
        }
        if (venta.getEstado() == EstadoVenta.CANCELADA) {
            throw new Exception("La venta ya fue cancelada previamente.");
        }
        venta.cancelar();
    }
    // Metodos de Clientes
    public void registrarCliente(Cliente cliente) throws Exception {
        if (buscarCliente(cliente.getIdentificacion()) != null) {
            throw new Exception("Ya existe un cliente con identificación: " + cliente.getIdentificacion());
        }
        clientes.add(cliente);
    }

    //actializacion de asientos
    public String obtenerMapaAsientos(String codigoFuncion) throws Exception {
        Funcion funcion = buscarFuncion(codigoFuncion);
        if (funcion == null) {
            throw new Exception("Función no encontrada.");
        }

        StringBuilder mapa = new StringBuilder();
        mapa.append("=== MAPA DE ASIENTOS - FUNCIÓN ").append(funcion.getCodigo()).append(" ===\n");

        String filaActual = "";
        for (Asiento a : funcion.getAsientos()) {
            // Suponiendo que el código es tipo "A1", "B2", extraemos la letra de la fila
            String fila = a.getCodigo().substring(0, 1);
            if (!fila.equals(filaActual)) {
                filaActual = fila;
                mapa.append("\nFila ").append(filaActual).append(": ");
            }
            if (a.estaDisponible()) {
                mapa.append("[").append(a.getCodigo()).append("] ");
            } else {
                mapa.append("[ XX ] ");
            }
        }
        return mapa.toString();
    }
    // Métodos de Búsqueda
    public Pelicula buscarPelicula(String codigo) {
        for (Pelicula p : peliculas) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public Sala buscarSala(String codigo) {
        for (Sala s : salas) {
            if (s.getCodigo().equalsIgnoreCase(codigo)) {
                return s;
            }
        }
        return null;
    }

    public Funcion buscarFuncion(String codigo) {
        for (Funcion f : funciones) {
            if (f.getCodigo().equalsIgnoreCase(codigo)) {
                return f;
            }
        }
        return null;
    }

    public Venta buscarVenta(String codigo) {
        for (Venta v : ventas) {
            if (v.getCodigo().equalsIgnoreCase(codigo)) {
                return v;
            }
        }
        return null;
    }

    public Cliente buscarCliente(String identificacion) {
        for (Cliente c : clientes) {
            if (c.getIdentificacion().equalsIgnoreCase(identificacion)) {
                return c;
            }
        }
        return null;
    }


    // Reportes y Estadísticas
    public int totalEntradasVendidas() {
        int total = 0;
        for (Venta v : ventas) {
            if (v.estaActiva()) {
                total += v.getCantidadEntradas();
            }
        }
        return total;
    }

    public double totalIngresos() {
        double total = 0;
        for (Venta v : ventas) {
            if (v.estaActiva()) {
                total += v.getValorTotalPagado();
            }
        }
        return total;
    }

    public Pelicula peliculaMasVendida() {
        Pelicula masVendida = null;
        int maxBoletas = 0;

        for (Pelicula p : peliculas) {
            int contadorBoletas = 0;
            for (Venta v : ventas) {
                if (v.estaActiva() && v.getFuncion().getPelicula().getCodigo().equals(p.getCodigo())) {
                    contadorBoletas += v.getCantidadEntradas();
                }
            }
            if (contadorBoletas > maxBoletas) {
                maxBoletas = contadorBoletas;
                masVendida = p;
            }
        }
        return masVendida;
    }

    public String ocupacionPorFuncion(String codFuncion) {
        Funcion funcion = buscarFuncion(codFuncion);
        if (funcion == null) {
            return "Función no encontrada.";
        }
        int ocupados = funcion.contarOcupados();
        int total = funcion.getAsientos().size();
        int libres = total - ocupados;
        return "Ocupados: " + ocupados + " / Libres: " + libres + " (Total: " + total + ")";
    }

    public List<Funcion> funcionesPorFecha(LocalDateTime fecha) {
        List<Funcion> resultado = new ArrayList<>();
        for (Funcion f : funciones) {
            if (f.getFecha().equals(fecha)) {
                resultado.add(f);
            }
        }
        return resultado;
    }
}