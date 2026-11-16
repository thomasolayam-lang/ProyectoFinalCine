package uniquindio.edu.co.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {

    private String codigo;
    private Cliente cliente;
    private Funcion funcion;
    private List<DetalleVenta> detalles;
    private LocalDateTime fechaHora;
    private double valorTotalPagado;
    private EstadoVenta estado;

    public Venta(String codigo, Cliente cliente, Funcion funcion, List<DetalleVenta> detalles) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.funcion = funcion;
        this.detalles = detalles != null ? detalles : new ArrayList<>();
        this.fechaHora = LocalDateTime.now();
        this.estado = EstadoVenta.ACTIVA;
        this.valorTotalPagado = calcularTotal();
    }

    private double calcularTotal() {
        double total = 0;
        for (DetalleVenta d : detalles) {
            total += d.getValor();
        }
        return total;
    }

    public void cancelar() {
        this.estado = EstadoVenta.CANCELADA;
        for (DetalleVenta d : detalles) {
            if (d.getAsiento() != null) {
                d.getAsiento().liberar();
            }
        }
    }

    public boolean estaActiva() {
        return this.estado == EstadoVenta.ACTIVA;
    }

    public int getCantidadEntradas() {
        return detalles.size();
    }


    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Funcion getFuncion() { return funcion; }
    public void setFuncion(Funcion funcion) { this.funcion = funcion; }
    public List<DetalleVenta> getDetalles() { return detalles; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public double getValorTotalPagado() { return valorTotalPagado; }
    public EstadoVenta getEstado() { return estado; }
    public void setEstado(EstadoVenta estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Venta:\n" +
                "codigo:" + codigo+"\n"+
                " cliente:" + cliente.getNombre()+"\n" +
                " funcion:" + funcion.getCodigo()+"\n" +
                " entradas:" + detalles+"\n"+
                " valor Total Pagado:" + valorTotalPagado +"\n"+
                " estado:" + estado;
    }
}