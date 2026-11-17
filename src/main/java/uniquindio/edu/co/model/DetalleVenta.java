package uniquindio.edu.co.model;

public class DetalleVenta {
    private Asiento asiento;
    private TipoCliente tipoCliente;
    private double valor;

    public DetalleVenta(Asiento asiento, TipoCliente tipoEntrada, double valorBaseFuncion) {
        this.asiento = asiento;
        this.tipoCliente = tipoCliente;
        this.valor = tipoEntrada.calcularTarifa(valorBaseFuncion);
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public TipoCliente getTipoEntrada() {
        return tipoCliente;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Asiento: " + asiento.getCodigo() + " (" + tipoCliente + ") - $" + valor;
    }
}