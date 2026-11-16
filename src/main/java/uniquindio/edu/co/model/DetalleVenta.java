package uniquindio.edu.co.model;

public class DetalleVenta {
    private Asiento asiento;
    private TipoCliente tipoEntrada;
    private double valor;

    public DetalleVenta(Asiento asiento, TipoCliente tipoEntrada, double valorBaseFuncion) {
        this.asiento = asiento;
        this.tipoEntrada = tipoEntrada;
        this.valor = tipoEntrada.calcularTarifa(valorBaseFuncion);
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public TipoCliente getTipoEntrada() {
        return tipoEntrada;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Asiento: " + asiento.getCodigo() + " (" + tipoEntrada + ") - $" + valor;
    }
}