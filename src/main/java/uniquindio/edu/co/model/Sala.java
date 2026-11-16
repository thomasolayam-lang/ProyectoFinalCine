package uniquindio.edu.co.model;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private String codigo;
    private String nombre;
    private int capacidad;
    private EstadoSala estado;
    private List<Asiento> asientos;

    public Sala(String codigo, String nombre, int capacidad, EstadoSala estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = estado;
        this.asientos = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoSala getEstado() {
        return estado;
    }

    public void setEstado(EstadoSala estado) {
        this.estado = estado;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", capacidad=" + capacidad +
                ", estado=" + estado +
                ", asientos=" + asientos +
                '}';
    }
    public void generarAsientos(int filas, int columnas) {
        this.asientos = new ArrayList<>();
        for (int i = 0; i < filas; i++) {
            char filaChar = (char) ('A' + i);
            for (int j = 1; j <= columnas; j++) {
                String cod = "" + filaChar + j;
                this.asientos.add(new Asiento(cod, filaChar, j, EstadoAsiento.DISPONIBLE));
            }
        }
    }
}
