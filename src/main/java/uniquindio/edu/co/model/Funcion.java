package uniquindio.edu.co.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Funcion {
    private String codigo;
    private Pelicula pelicula;
    private Sala sala;
    private LocalDate fecha;
    private LocalTime hora;
    private double valorBase;
    private EstadoFuncion estado;
    private List<Asiento> asientos;

    public Funcion(String codigo, Pelicula pelicula, Sala sala, LocalDate fecha, LocalTime hora, EstadoFuncion estado, List<Asiento> asientos) {
        this.codigo = codigo;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.asientos = asientos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public EstadoFuncion getEstado() {
        return estado;
    }

    public void setEstado(EstadoFuncion estado) {
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
        return "Funcion{" +
                "asientos=" + asientos +
                ", codigo='" + codigo + '\'' +
                ", pelicula=" + pelicula +
                ", sala=" + sala +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", valorBase=" + valorBase +
                ", estado=" + estado +
                '}';
    }
    private void copiarAsientosDeSala(Sala sala) {
        if (sala != null && sala.getAsientos() != null) {
            for (Asiento a : sala.getAsientos()) {
                this.asientos.add(new Asiento(a.getCodigo(), a.getFila(), a.getNumero(), EstadoAsiento.DISPONIBLE));
            }
        }
    }
    public Asiento buscarAsiento(String codigoAsiento) {
        for (Asiento a : asientos) {
            if (a.getCodigo().equalsIgnoreCase(codigoAsiento)) {
                return a;
            }
        }
        return null;
    }
    public int contarDisponibles() {
        int contador = 0;
        for (Asiento a : asientos) {
            if (a.estaDisponible()) {
                contador++;
            }
        }
        return contador;
    }
    public int contarOcupados() {
        return asientos.size() - contarDisponibles();
    }
    public void cancelar(){
        this.estado= EstadoFuncion.CANCELADA;
    }
}
