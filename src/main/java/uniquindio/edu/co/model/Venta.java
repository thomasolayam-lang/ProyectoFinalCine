package uniquindio.edu.co.model;

import java.time.LocalDateTime;
public class Venta {

        private String codigo;
        private Cliente cliente;
        private Funcion funcion;
        private Asiento asiento;
        private TipoCliente tipoEntrada;
        private LocalDateTime fechaHora;
        private double valorPagado;
        private EstadoVenta estado;

        public Venta(String codigo, Cliente cliente, Funcion funcion, Asiento asiento, TipoCliente tipoEntrada, double valorPagado, EstadoVenta estado) {
            this.codigo = codigo;
            this.cliente = cliente;
            this.funcion = funcion;
            this.asiento = asiento;
            this.tipoEntrada = tipoEntrada;
            this.valorPagado = valorPagado;
            this.fechaHora = LocalDateTime.now();
            this.estado = estado;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public Cliente getCliente() {
            return cliente;
        }

        public void setCliente(Cliente cliente) {
            this.cliente = cliente;
        }

        public Funcion getFuncion() {
            return funcion;
        }

        public void setFuncion(Funcion funcion) {
            this.funcion = funcion;
        }

        public Asiento getAsiento() {
            return asiento;
        }

        public void setAsiento(Asiento asiento) {
            this.asiento = asiento;
        }

        public TipoCliente getTipoEntrada() {
            return tipoEntrada;
        }

        public void setTipoEntrada(TipoCliente tipoEntrada) {
            this.tipoEntrada = tipoEntrada;
        }

        public LocalDateTime getFechaHora() {
            return fechaHora;
        }

        public void setFechaHora(LocalDateTime fechaHora) {
            this.fechaHora = fechaHora;
        }

        public double getValorPagado() {
            return valorPagado;
        }

        public void setValorPagado(double valorPagado) {
            this.valorPagado = valorPagado;
        }

        public EstadoVenta getEstado() {
            return estado;
        }

        public void setEstado(EstadoVenta estado) {
            this.estado = estado;
        }

    @Override
    public String toString() {
        return "Venta{" +
                "asiento=" + asiento +
                ", codigo='" + codigo + '\'' +
                ", cliente=" + cliente +
                ", funcion=" + funcion +
                ", tipoEntrada=" + tipoEntrada +
                ", fechaHora=" + fechaHora +
                ", valorPagado=" + valorPagado +
                ", estado=" + estado +
                '}';
    }

    public void cancelar() {
        this.estado = EstadoVenta.CANCELADA;
        if (this.asiento != null) {
            this.asiento.liberar();
        }
    }
    public boolean estaActiva() {
        return this.estado == EstadoVenta.ACTIVA;
    }
}
