package uniquindio.edu.co.model;

public enum TipoCliente {
    GENERAL(1.0),
    ESTUDIANTE(0.8),
    NINO(0.7),
    ADULTO_MAYOR(0.75);

    private final double porcentajeTarifa;

    TipoCliente(double porcentajeTarifa) {
        this.porcentajeTarifa = porcentajeTarifa;
    }

    public double calcularTarifa(double valorBase) {
        return valorBase * porcentajeTarifa;
    }
}
