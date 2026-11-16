package uniquindio.edu.co;

import uniquindio.edu.co.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Cinema cinema = new Cinema("CINEMAUQ");

    public static void main(String[] args) {
        cargarDatosPrueba();

        int opcion = 0;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> opcionVentaEntrada();
                    case 2 -> opcionCancelarVenta();
                    case 3 -> opcionConsultarPeliculas();
                    case 4 -> opcionConsultarFunciones();
                    case 5 -> opcionConsultarDisponibilidad();
                    case 6 -> opcionAdministrarPeliculas();
                    case 7 -> opcionAdministrarFunciones();
                    case 8 -> opcionAdministrarSalas();
                    case 9 -> opcionGenerarReportes();
                    case 10 -> System.out.println("Saliendo del sistema CINEMAUQ...");
                    default -> System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        } while (opcion != 10);
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("=========================================");
        System.out.println("                CINEMAUQ                 ");
        System.out.println("=========================================");
        System.out.println("1. Venta de entrada");
        System.out.println("2. Cancelar venta");
        System.out.println("3. Consultar películas");
        System.out.println("4. Consultar funciones");
        System.out.println("5. Consultar disponibilidad");
        System.out.println("6. Administrar películas");
        System.out.println("7. Administrar funciones");
        System.out.println("8. Administrar salas");
        System.out.println("9. Generar reportes");
        System.out.println("10. Salir");
        System.out.println("=========================================");
    }

    // =========================================================================
    // 1. VENTA DE ENTRADA (Múltiples asientos con Map<String, TipoCliente>)
    // =========================================================================
    private static void opcionVentaEntrada() throws Exception {
        System.out.println("\n--- VENTA DE ENTRADAS ---");
        opcionConsultarFunciones();

        System.out.print("Ingrese el código de la función (ej. F-01): ");
        String codFuncion = scanner.nextLine().trim().toUpperCase();

        // Mostrar mapa visual de asientos antes de elegir
        System.out.println(cinema.obtenerMapaAsientos(codFuncion));

        // Datos del cliente
        System.out.print("\nIngrese la identificación del cliente: ");
        String idCliente = scanner.nextLine().trim();
        Cliente cliente = cinema.buscarCliente(idCliente);

        if (cliente == null) {
            System.out.print("Cliente nuevo. Ingrese el nombre completo: ");
            String nombre = scanner.nextLine().trim();
            cliente = new Cliente();
        } else {
            System.out.println("Cliente registrado: " + cliente.getNombre());
        }

        // Selección iterativa de asientos y tipos de cliente usando Map
        int cantidad = leerEntero("¿Cuántas entradas desea comprar?: ");
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor a 0.");
            return;
        }

        Map<String, TipoCliente> asientosYTipos = new LinkedHashMap<>();

        for (int i = 1; i <= cantidad; i++) {
            System.out.println("\n--- Configuración Entrada #" + i + " ---");
            System.out.print("Código del asiento (ej. A1): ");
            String codAsiento = scanner.nextLine().trim().toUpperCase();

            if (asientosYTipos.containsKey(codAsiento)) {
                System.out.println("Ya seleccionaste el asiento " + codAsiento + " en esta compra. Elige otro.");
                i--;
                continue;
            }

            TipoCliente tipo = seleccionarTipoCliente();
            asientosYTipos.put(codAsiento, tipo);
        }

        // Registrar la venta en Cinema
        Venta ventaRealizada = cinema.venderEntradas(codFuncion, cliente, asientosYTipos);

        System.out.println("\n¡VENTA REGISTRADA EXITOSAMENTE!");
        System.out.println("Código de Venta: " + ventaRealizada.getCodigo());
        System.out.println("Cliente: " + ventaRealizada.getCliente().getNombre());
        System.out.println("Película: " + ventaRealizada.getFuncion().getPelicula().getTitulo());
        System.out.println("Detalle de entradas:");
        for (DetalleVenta detalle : ventaRealizada.getDetalles()) {
            System.out.println("  - " + detalle);
        }
        System.out.println("TOTAL PAGADO: $" + ventaRealizada.getValorTotalPagado());
    }

    private static TipoCliente seleccionarTipoCliente() {
        while (true) {
            System.out.println("Seleccione el tipo de entrada:");
            System.out.println("  1. General");
            System.out.println("  2. Estudiante");
            System.out.println("  3. Niño");
            System.out.println("  4. Adulto Mayor");
            int op = leerEntero("Opción: ");
            switch (op) {
                case 1 -> { return TipoCliente.GENERAL; }
                case 2 -> { return TipoCliente.ESTUDIANTE; }
                case 3 -> { return TipoCliente.NINO; }
                case 4 -> { return TipoCliente.ADULTO_MAYOR; }
                default -> System.out.println("Tipo inválido, intente de nuevo.");
            }
        }
    }

    // =========================================================================
    // 2. CANCELAR VENTA
    // =========================================================================
    private static void opcionCancelarVenta() throws Exception {
        System.out.println("\n--- CANCELAR VENTA ---");
        System.out.print("Ingrese el código de la venta a cancelar (ej. V-1): ");
        String codVenta = scanner.nextLine().trim().toUpperCase();

        cinema.cancelarVenta(codVenta);
        System.out.println("La venta " + codVenta + " ha sido cancelada y los asientos han sido liberados.");
    }

    // =========================================================================
    // 3. CONSULTAR PELÍCULAS
    // =========================================================================
    private static void opcionConsultarPeliculas() {
        System.out.println("\n--- PELÍCULAS DISPONIBLES ---");
        List<Pelicula> peliculas = cinema.consultarPeliculas();
        if (peliculas.isEmpty()) {
            System.out.println("No hay películas disponibles en cartelera.");
            return;
        }
        for (Pelicula p : peliculas) {
            System.out.println(p.getCodigo() + " | " + p.getTitulo() + " (" + p.getDuracionMinutos() + " min) - " + p.getGenero());
        }
    }

    // =========================================================================
    // 4. CONSULTAR FUNCIONES
    // =========================================================================
    private static void opcionConsultarFunciones() {
        System.out.println("\n--- FUNCIONES PROGRAMADAS ---");
        List<Funcion> funciones = cinema.consultarFunciones();
        if (funciones.isEmpty()) {
            System.out.println("No hay funciones programadas.");
            return;
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Funcion f : funciones) {
            System.out.println(f.getCodigo() + " | Película: " + f.getPelicula().getTitulo()
                    + " | Sala: " + f.getSala().getCodigo()
                    + " | Fecha: " + f.getFechaHora().format(fmt)
                    + " | Base: $" + f.getValorBase());
        }
    }

    // =========================================================================
    // 5. CONSULTAR DISPONIBILIDAD
    // =========================================================================
    private static void opcionConsultarDisponibilidad() throws Exception {
        System.out.println("\n--- DISPONIBILIDAD DE ASIENTOS ---");
        System.out.print("Ingrese el código de la función: ");
        String codFuncion = scanner.nextLine().trim().toUpperCase();

        System.out.println(cinema.obtenerMapaAsientos(codFuncion));
        int libres = cinema.consultarAsientosDisponiblesEnFuncion(codFuncion);
        System.out.println("\nTotal asientos disponibles: " + libres);
    }

    // =========================================================================
    // 6. ADMINISTRAR PELÍCULAS
    // =========================================================================
    private static void opcionAdministrarPeliculas() throws Exception {
        System.out.println("\n--- ADMINISTRAR PELÍCULAS ---");
        System.out.println("1. Registrar nueva película");
        System.out.println("2. Retirar película de cartelera");
        int op = leerEntero("Seleccione una opción: ");

        if (op == 1) {
            System.out.print("Código (ej. P-03): ");
            String cod = scanner.nextLine().trim().toUpperCase();
            System.out.print("Título: ");
            String titulo = scanner.nextLine().trim();
            int duracion = leerEntero("Duración en minutos: ");
            System.out.print("Género (ACCION, COMEDIA, DRAMA, TERROR, CIENCIA_FICCION): ");
            GeneroPelicula genero = GeneroPelicula.valueOf(scanner.nextLine().trim().toUpperCase());

            Pelicula nueva = new Pelicula(cod, titulo, duracion, genero, EstadoPelicula.DISPONIBLE);
            cinema.registrarPelicula(nueva);
            System.out.println("Película registrada correctamente.");
        } else if (op == 2) {
            System.out.print("Código de la película a retirar: ");
            String cod = scanner.nextLine().trim().toUpperCase();
            Pelicula p = cinema.buscarPelicula(cod);
            if (p != null) {
                p.setEstado(EstadoPelicula.NO_DISPONIBLE);
                System.out.println("Película marcada como no disponible.");
            } else {
                System.out.println("Película no encontrada.");
            }
        }
    }

    // =========================================================================
    // 7. ADMINISTRAR FUNCIONES
    // =========================================================================
    private static void opcionAdministrarFunciones() throws Exception {
        System.out.println("\n--- ADMINISTRAR FUNCIONES ---");
        System.out.println("1. Programar nueva función");
        System.out.println("2. Cancelar función");
        int op = leerEntero("Seleccione una opción: ");

        if (op == 1) {
            System.out.print("Código de función (ej. F-03): ");
            String codFunc = scanner.nextLine().trim().toUpperCase();
            System.out.print("Código de película: ");
            String codPel = scanner.nextLine().trim().toUpperCase();
            System.out.print("Código de sala: ");
            String codSala = scanner.nextLine().trim().toUpperCase();
            System.out.print("Fecha y hora (yyyy-MM-dd HH:mm): ");
            String fechaStr = scanner.nextLine().trim();
            LocalDateTime fechaHora = LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            double valorBase = leerDouble("Valor base de la boleta: ");

            cinema.programarFuncion(codFunc, codPel, codSala, fechaHora, valorBase);
            System.out.println("Función programada exitosamente.");
        } else if (op == 2) {
            System.out.print("Código de función a cancelar: ");
            String codFunc = scanner.nextLine().trim().toUpperCase();
            Funcion f = cinema.buscarFuncion(codFunc);
            if (f != null) {
                f.setEstado(EstadoFuncion.CANCELADA);
                System.out.println("Función cancelada.");
            } else {
                System.out.println("Función no encontrada.");
            }
        }
    }

    // =========================================================================
    // 8. ADMINISTRAR SALAS
    // =========================================================================
    private static void opcionAdministrarSalas() throws Exception {
        System.out.println("\n--- ADMINISTRAR SALAS ---");
        System.out.print("Código de sala (ej. S-03): ");
        String cod = scanner.nextLine().trim().toUpperCase();
        System.out.print("Nombre de la sala: ");
        String nombre = scanner.nextLine().trim();
        int filas = leerEntero("Número de filas (ej. 5): ");
        int columnas = leerEntero("Asientos por fila (ej. 6): ");

        Sala nuevaSala = new Sala(cod, nombre, filas, columnas);
        cinema.registrarSala(nuevaSala);
        System.out.println("Sala registrada exitosamente.");
    }

    // =========================================================================
    // 9. GENERAR REPORTES
    // =========================================================================
    private static void opcionGenerarReportes() {
        System.out.println("\n================ REPORTES CINEMAUQ ================");
        System.out.println("Total de entradas vendidas (activas): " + cinema.totalEntradasVendidas());
        System.out.println("Total de ingresos recaudados: $" + cinema.totalIngresos());

        Pelicula top = cinema.peliculaMasVendida();
        if (top != null) {
            System.out.println("Película más vendida: " + top.getTitulo() + " (" + top.getCodigo() + ")");
        } else {
            System.out.println("Película más vendida: Aún no hay ventas registradas.");
        }
        System.out.println("===================================================");
    }

    // =========================================================================
    // MÉTODOS AUXILIARES DE LECTURA Y DATOS INICIALES
    // =========================================================================
    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número entero válido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un valor numérico válido.");
            }
        }
    }

    private static void cargarDatosPrueba() {
        try {
            Sala sala1 = new Sala("S-01", "Sala 1 - General", 4, 5);
            Sala sala2 = new Sala("S-02", "Sala 2 - VIP", 3, 4);
            cinema.registrarSala(sala1);
            cinema.registrarSala(sala2);

            Pelicula p1 = new Pelicula("P-01", "Dune: Parte Dos", 166, GeneroPelicula.CIENCIA_FICCION, EstadoPelicula.DISPONIBLE);
            Pelicula p2 = new Pelicula("P-02", "Intensamente 2", 96, GeneroPelicula.COMEDIA, EstadoPelicula.DISPONIBLE);
            cinema.registrarPelicula(p1);
            cinema.registrarPelicula(p2);

            cinema.programarFuncion("F-01", "P-01", "S-01", LocalDateTime.now().plusHours(2), 15000);
            cinema.programarFuncion("F-02", "P-02", "S-02", LocalDateTime.now().plusHours(4), 12000);
        } catch (Exception e) {
            System.out.println("Error cargando datos iniciales: " + e.getMessage());
        }
    }
}