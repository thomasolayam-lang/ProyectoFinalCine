package uniquindio.edu.co;

import uniquindio.edu.co.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Cinema cinema = new Cinema("CinemaUQ");

    public static void main(String[] args) {
        cargarDatosPrueba();

        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> menuPeliculas();
                    case 2 -> menuSalas();
                    case 3 -> menuFunciones();
                    case 4 -> menuClientes();
                    case 5 -> menuVentas();
                    case 6 -> menuReportes();
                    case 0 -> System.out.println("\nSaliendo del sistema de gestión de " + cinema.getNombre() + "...");
                    default -> System.out.println("\n[!] Opción no válida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("\n[ERROR] " + e.getMessage());
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n==========================================");
        System.out.println("              " + cinema.getNombre().toUpperCase());
        System.out.println("==========================================");
        System.out.println("1. Gestión de Películas");
        System.out.println("2. Gestión de Salas");
        System.out.println("3. Gestión de Funciones");
        System.out.println("4. Gestión de Clientes");
        System.out.println("5. Taquilla (Vender / Cancelar Entradas)");
        System.out.println("6. Consultas y Reportes");
        System.out.println("0. Salir");
        System.out.println("==========================================");
    }

    // ==================== 1. PELÍCULAS ====================
    private static void menuPeliculas() throws Exception {
        int op;
        do {
            System.out.println("\n--- GESTIÓN DE PELÍCULAS ---");
            System.out.println("1. Registrar película");
            System.out.println("2. Modificar película");
            System.out.println("3. Deshabilitar película");
            System.out.println("4. Listar películas disponibles");
            System.out.println("0. Volver al menú principal");
            op = leerEntero("Opción: ");

            switch (op) {
                case 1 -> {
                    String codigo = leerTexto("Código (ej. P01): ");
                    Pelicula pelicula= cinema.buscarPelicula(codigo);
                    if (pelicula!= null) throw new Exception("Ya exite una pelicula con el codigo:"+codigo);
                    String nombre = leerTexto("Nombre: ");
                    String genero = leerTexto("Género: ");
                    int duracion = leerEntero("Duración en minutos: ");
                    Clasificacion clasif = seleccionarClasificacion();
                    Pelicula p = new Pelicula(codigo, nombre, genero, duracion, clasif, EstadoPelicula.DISPONIBLE);
                    cinema.registrarPelicula(p);
                    System.out.println("Película registrada con éxito.");
                }
                case 2 -> {
                    String codigo = leerTexto("Código de la película a modificar: ");
                    if (cinema.buscarPelicula(codigo) == null) {
                        throw new Exception("No existe película con código " + codigo);
                    }
                    String nombre = leerTexto("Nuevo nombre: ");
                    String genero = leerTexto("Nuevo género: ");
                    int duracion = leerEntero("Nueva duración (min): ");
                    Clasificacion clasif = seleccionarClasificacion();
                    EstadoPelicula estado = leerEntero("Estado (1. DISPONIBLE, 2. NO_DISPONIBLE): ") == 1
                            ? EstadoPelicula.DISPONIBLE : EstadoPelicula.NO_DISPONIBLE;
                    Pelicula actualizada = new Pelicula(codigo, nombre, genero, duracion, clasif, estado);
                    cinema.modificarPelicula(codigo, actualizada);
                    System.out.println("Película actualizada con éxito.");
                }
                case 3 -> {
                    String codigo = leerTexto("Código de la película a deshabilitar: ");
                    cinema.deshabilitarPelicula(codigo);
                    System.out.println("Película deshabilitada.");
                }
                case 4 -> {
                    List<Pelicula> lista = cinema.consultarPeliculas();
                    if (lista.isEmpty()) {
                        System.out.println("No hay películas disponibles.");
                    } else {
                        lista.forEach(p -> System.out.printf(" - [%s] %s | %s | %d min | %s%n",
                                p.getCodigo(), p.getNombre(), p.getGenero(), p.getDuracionMin(), p.getClasificacion()));
                    }
                }
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }

    // ==================== 2. SALAS ====================
    private static void menuSalas() throws Exception {
        int op;
        do {


            System.out.println("\n--- GESTIÓN DE SALAS ---");
            System.out.println("1. Registrar sala y generar asientos");
            System.out.println("2. Deshabilitar sala");
            System.out.println("3. Listar salas");
            System.out.println("0. Volver al menú principal");
            op = leerEntero("Opción: ");

            switch (op) {
                case 1 -> {
                    String codigo = leerTexto("Código de sala (ej. S01): ");
                    Sala salaB= cinema.buscarSala(codigo);
                    if (salaB != null) throw new Exception("Ya exite una sala con el codigo:"+codigo);

                    String nombre = leerTexto("Nombre de sala: ");
                    int filas = leerEntero("Cantidad de filas (ej. 5 -> A..E): ");
                    int columnas = leerEntero("Asientos por fila (ej. 8): ");
                    int capacidad = filas * columnas;

                    Sala sala = new Sala(codigo, nombre, capacidad, EstadoSala.DISPONIBLE);
                    sala.generarAsientos(filas, columnas);
                    cinema.registrarSala(sala);
                    System.out.println("Sala registrada con " + capacidad + " asientos generados.");
                }
                case 2 -> {
                    String codigo = leerTexto("Código de sala a deshabilitar: ");
                    cinema.deshabilitarSala(codigo);
                    System.out.println("Sala marcada como FUERA_DE_SERVICIO.");
                }
                case 3 -> {
                    for (Sala s : cinema.getSalas()) {
                        System.out.printf(" - [%s] %s | Capacidad: %d | Estado: %s%n",
                                s.getCodigo(), s.getNombre(), s.getCapacidad(), s.getEstado());
                    }
                }
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }


    // ==================== 3. FUNCIONES ====================
    private static void menuFunciones() throws Exception {
        int op;

        do {
            System.out.println("\n--- GESTIÓN DE FUNCIONES ---");
            System.out.println("1. Programar función");
            System.out.println("2. Cancelar función");
            System.out.println("3. Listar funciones programadas");
            System.out.println("4. Ver mapa de asientos de una función");
            System.out.println("0. Volver al menú principal");
            op = leerEntero("Opción: ");

            switch (op) {
                case 1 -> {
                    String codFuncion = leerTexto("Código de función (ej. F01): ");
                    Funcion funcionB= cinema.buscarFuncion(codFuncion);
                    if (funcionB != null) throw new Exception("Ya exite una funcion con el codigo:"+codFuncion);
                    String codPelicula = leerTexto("Código de película: ");
                    Pelicula pelicula = cinema.buscarPelicula(codPelicula);
                    if (pelicula == null) throw new Exception("Película no encontrada.");

                    String codSala = leerTexto("Código de sala: ");
                    Sala sala = cinema.buscarSala(codSala);
                    if (sala == null) throw new Exception("Sala no encontrada.");

                    LocalDate fecha = leerFecha("Fecha (YYYY-MM-DD): ");
                    LocalTime hora = leerHora("Hora (HH:MM): ");
                    double valorBase = leerDouble("Valor base de la boleta ($): ");

                    Funcion funcion = new Funcion(codFuncion, pelicula, sala, fecha, hora, valorBase, EstadoFuncion.PROGRAMADA);
                    cinema.programarFuncion(funcion);
                    System.out.println("Función programada exitosamente.");
                }
                case 2 -> {
                    String codFuncion = leerTexto("Código de función a cancelar: ");
                    cinema.cancelarFuncion(codFuncion);
                    System.out.println("Función cancelada.");
                }
                case 3 -> {
                    List<Funcion> funciones = cinema.consultarFunciones();
                    if (funciones.isEmpty()) {
                        System.out.println("No hay funciones programadas.");
                    } else {
                        for (Funcion f : funciones) {
                            System.out.printf(" - [%s] %s | Sala: %s | %s %s | Base: $%.2f | Libres: %d%n",
                                    f.getCodigo(), f.getPelicula().getNombre(), f.getSala().getNombre(),
                                    f.getFecha(), f.getHora(), f.getValorBase(), f.contarDisponibles());
                        }
                    }
                }
                case 4 -> {
                    String codFuncion = leerTexto("Código de la función: ");
                    System.out.println("\n" + cinema.obtenerMapaAsientos(codFuncion));
                }
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }

    // ==================== 4. CLIENTES ====================
    private static void menuClientes() throws Exception {
        int op;
        do {


            System.out.println("\n--- GESTIÓN DE CLIENTES ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("0. Volver al menú principal");
            op = leerEntero("Opción: ");

            switch (op) {
                case 1 -> {
                    String id = leerTexto("Identificación: ");
                    Cliente cliente= cinema.buscarCliente(id);
                    if (cliente != null) throw new Exception("Ya exite un cliente con el id :"+id);
                    String nombre = leerTexto("Nombre completo: ");
                    TipoCliente tipo = seleccionarTipoCliente();
                    cinema.registrarCliente(new Cliente(id, nombre, tipo));
                    System.out.println("Cliente registrado con éxito.");
                }
                case 2 -> {
                    for (Cliente c : cinema.getClientes()) {
                        System.out.printf(" - ID: %s | %s | Tipo por defecto: %s%n",
                                c.getIdentificacion(), c.getNombre(), c.getTipoCliente());
                    }
                }
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        }while (op != 0);
    }

    // ==================== 5. VENTAS (TAQUILLA) ====================
    private static void menuVentas() throws Exception {
        int op;
        do {


        System.out.println("\n--- TAQUILLA ---");
        System.out.println("1. Vender entradas");
        System.out.println("2. Cancelar venta");
        System.out.println("3. Listar ventas registradas");
        System.out.println("0. Volver al menú principal");
        op = leerEntero("Opción: ");

        switch (op) {
            case 1 -> {
                List<Funcion> programadas = cinema.consultarFunciones();
                if (programadas.isEmpty()) {
                    throw new Exception("No hay funciones programadas disponibles para venta.");
                }
                System.out.println("\nFunciones disponibles:");
                for (Funcion f : programadas) {
                    System.out.printf(" - [%s] %s (%s %s) - Sala %s - Base: $%.2f%n",
                            f.getCodigo(), f.getPelicula().getNombre(), f.getFecha(),
                            f.getHora(), f.getSala().getNombre(), f.getValorBase());
                }

                String codFuncion = leerTexto("\nIngrese el código de la función: ");
                Funcion funcionB= cinema.buscarFuncion(codFuncion);
                if (funcionB == null) throw new Exception("No exite una funcion con el codigo:"+codFuncion);
                System.out.println("\n" + cinema.obtenerMapaAsientos(codFuncion));

                String idCliente = leerTexto("\nIdentificación del cliente: ");
                Cliente cliente = cinema.buscarCliente(idCliente);
                if (cliente == null) {
                    System.out.println("Cliente nuevo. Ingrese sus datos:");
                    String nombre = leerTexto("Nombre del cliente: ");
                    TipoCliente tipoPrincipal = seleccionarTipoCliente();
                    cliente = new Cliente(idCliente, nombre, tipoPrincipal);
                } else {
                    System.out.println("Cliente encontrado: " + cliente.getNombre());
                }

                int cantidad = leerEntero("¿Cuántas entradas desea comprar?: ");
                if (cantidad <= 0) throw new Exception("La cantidad debe ser mayor a 0.");

                Map<String, TipoCliente> seleccion = new LinkedHashMap<>();
                for (int i = 1; i <= cantidad; i++) {
                    String codAsiento = leerTexto("Código del asiento #" + i + " (ej. A1): ").toUpperCase();
                    if (seleccion.containsKey(codAsiento)) {
                        throw new Exception("Ya seleccionaste el asiento " + codAsiento + " en esta compra.");
                    }
                    System.out.println("Seleccione tarifa para el asiento " + codAsiento + ":");
                    TipoCliente tipoEntrada = seleccionarTipoCliente();
                    seleccion.put(codAsiento, tipoEntrada);
                }

                Venta venta = cinema.venderEntradas(codFuncion, cliente, seleccion);
                System.out.println("\n¡VENTA EXITOSA!");
                System.out.println(venta);
            }
            case 2 -> {
                String codVenta = leerTexto("Código de venta a cancelar (ej. V-1): ");
                cinema.cancelarVenta(codVenta);
                System.out.println("Venta cancelada y asientos liberados.");
            }
            case 3 -> {
                if (cinema.getVentas().isEmpty()) {
                    System.out.println("Aún no hay ventas registradas.");
                } else {
                    for (Venta v : cinema.getVentas()) {
                        System.out.println(v);
                        System.out.println("------------------------------------------");
                    }
                }
            }
            case 0 -> System.out.println("Volviendo al menú principal...");
            default -> System.out.println("Opción inválida.");
        }
        } while (op != 0);
    }

    // ==================== 6. REPORTES ====================
    private static void menuReportes() {

        System.out.println("\n--- REPORTES Y ESTADÍSTICAS ---");
        System.out.println("Total entradas vendidas (activas): " + cinema.totalEntradasVendidas());
        System.out.printf("Total ingresos recaudados: $%.2f%n", cinema.totalIngresos());

        Pelicula top = cinema.peliculaMasVendida();
        System.out.println("Película más vendida: " + (top != null ? top.getNombre() : "Sin ventas aún"));

        String codFuncion = leerTexto("\nIngrese código de función para ver ocupación (o Enter para omitir): ");
        if (!codFuncion.isEmpty()) {
            System.out.println(cinema.ocupacionPorFuncion(codFuncion));
        }
    }

    // ==================== UTILIDADES DE LECTURA ====================
    private static Clasificacion seleccionarClasificacion() {
        System.out.println("Clasificación: 1. TODOS | 2. MAYORES_7 | 3. MAYORES_12 | 4. MAYORES_15 | 5. MAYORES_18");
        int op = leerEntero("Seleccione: ");
        return switch (op) {
            case 2 -> Clasificacion.MAYORES_7;
            case 3 -> Clasificacion.MAYORES_12;
            case 4 -> Clasificacion.MAYORES_15;
            case 5 -> Clasificacion.MAYORES_18;
            default -> Clasificacion.TODOS;
        };
    }

    private static TipoCliente seleccionarTipoCliente() {
        System.out.println("Tipo tarifa: 1. GENERAL (100%) | 2. ESTUDIANTE (80%) | 3. NINO (70%) | 4. ADULTO_MAYOR (75%)");
        int op = leerEntero("Seleccione: ");
        return switch (op) {
            case 2 -> TipoCliente.ESTUDIANTE;
            case 3 -> TipoCliente.NINO;
            case 4 -> TipoCliente.ADULTO_MAYOR;
            default -> TipoCliente.GENERAL;
        };
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[!] Ingrese un número entero válido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[!] Ingrese un valor numérico válido.");
            }
        }
    }

    private static LocalDate leerFecha(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("[!] Formato inválido. Use YYYY-MM-DD (ej. 2026-11-20).");
            }
        }
    }

    private static LocalTime leerHora(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return LocalTime.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("[!] Formato inválido. Use HH:MM (ej. 19:30).");
            }
        }
    }

    // ==================== DATOS DE PRUEBA ====================
    private static void cargarDatosPrueba() {
        try {
            Pelicula p1 = new Pelicula("P01", "Dune: Parte Dos", "Ciencia Ficción", 166, Clasificacion.MAYORES_12, EstadoPelicula.DISPONIBLE);
            Pelicula p2 = new Pelicula("P02", "Intensamente 2", "Animación", 96, Clasificacion.TODOS, EstadoPelicula.DISPONIBLE);
            cinema.registrarPelicula(p1);
            cinema.registrarPelicula(p2);

            Sala s1 = new Sala("S01", "Sala IMAX 1", 30, EstadoSala.DISPONIBLE);
            s1.generarAsientos(5, 6); // Filas A-E, columnas 1-6
            Sala s2 = new Sala("S02", "Sala VIP 2", 20, EstadoSala.DISPONIBLE);
            s2.generarAsientos(4, 5); // Filas A-D, columnas 1-5
            cinema.registrarSala(s1);
            cinema.registrarSala(s2);

            Funcion f1 = new Funcion("F01", p1, s1, LocalDate.now().plusDays(1), LocalTime.of(19, 30), 18000, EstadoFuncion.PROGRAMADA);
            Funcion f2 = new Funcion("F02", p2, s2, LocalDate.now().plusDays(1), LocalTime.of(16, 0), 15000, EstadoFuncion.PROGRAMADA);
            cinema.programarFuncion(f1);
            cinema.programarFuncion(f2);

            cinema.registrarCliente(new Cliente("1094001", "Laura Gómez", TipoCliente.ESTUDIANTE));
            cinema.registrarCliente(new Cliente("1094002", "Carlos Ruiz", TipoCliente.GENERAL));
        } catch (Exception e) {
            System.out.println("Error cargando datos iniciales: " + e.getMessage());
        }
    }
}