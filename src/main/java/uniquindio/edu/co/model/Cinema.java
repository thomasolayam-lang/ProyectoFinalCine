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
    public List<Venta> consultarVentasPorCliente(String cedulaCliente) {
        List<Venta> resultado = new ArrayList<>();
        for (Venta v : ventas) {
            if (v.getCliente().getIdentificacion().equalsIgnoreCase(cedulaCliente)) {
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

    // --- Implementación de Buscable<Object> ---
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

// --- Gestión de Películas ---
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

// --- Gestión de Salas ---
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

// --- Gestión de Funciones ---
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

// --- Gestión de Clientes ---
public void registrarCliente(Cliente cliente) throws Exception {
    if (buscarCliente(cliente.getIdentificacion()) != null) {
        throw new Exception("Ya existe un cliente con identificación: " + cliente.getIdentificacion());
    }
    clientes.add(cliente);
}

// --- Gestión de Ventas ---
public Venta venderEntrada(String codigoFuncion, Cliente cliente, TipoCliente tipo, String codAsiento) throws Exception {
    Funcion funcion = buscarFuncion(codigoFuncion);
    if (funcion == null) {
        throw new Exception("Función no encontrada.");
    }
    if (funcion.getEstado() != EstadoFuncion.PROGRAMADA) {
        throw new Exception("La función no está programada para venta.");
    }
    Asiento asiento = funcion.buscarAsiento(codAsiento);
    if (asiento == null) {
        throw new Exception("El asiento " + codAsiento + " no existe en esta función.");
    }
    if (!asiento.estaDisponible()) {
        throw new Exception("El asiento " + codAsiento + " ya está ocupado.");
    }

    if (buscarCliente(cliente.getIdentificacion()) == null) {
        clientes.add(cliente);
    }

    asiento.ocupar();
    double valorPagado = tipo.calcularTarifa(funcion.getValorBase());
    String codigoVenta = "V-" + (ventas.size() + 1);
    Venta venta = new Venta(codigoVenta, cliente, funcion, asiento, tipo, LocalDateTime.now(), valorPagado);
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
    venta.getAsiento().liberar();
}

// --- Métodos de Búsqueda ---
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

// --- Implementación de Consultable ---
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

// --- Reportes y Estadísticas ---
public int totalEntradasVendidas() {
    int total = 0;
    for (Venta v : ventas) {
        if (v.getEstado() == EstadoVenta.ACTIVA) {
            total++;
        }
    }
    return total;
}

public double totalIngresos() {
    double total = 0;
    for (Venta v : ventas) {
        if (v.getEstado() == EstadoVenta.ACTIVA) {
            total += v.getValorPagado();
        }
    }
    return total;
}

public Pelicula peliculaMasVendida() {
    Pelicula masVendida = null;
    int maxVentas = 0;

    for (Pelicula p : peliculas) {
        int contador = 0;
        for (Venta v : ventas) {
            if (v.getEstado() == EstadoVenta.ACTIVA &&
                    v.getFuncion().getPelicula().getCodigo().equals(p.getCodigo())) {
                contador++;
            }
        }
        if (contador > maxVentas) {
            maxVentas = contador;
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

public List<Funcion> funcionesPorFecha(LocalDate fecha) {
    List<Funcion> resultado = new ArrayList<>();
    for (Funcion f : funciones) {
        if (f.getFecha().equals(fecha)) {
            resultado.add(f);
        }
    }
    return resultado;
}

// --- Implementación de Buscable<Object> ---
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

