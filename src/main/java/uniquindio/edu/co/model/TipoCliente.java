package uniquindio.edu.co.model;

public enum TipoCliente {
    GENERAL(100),
    ESTUDIANTE(80),
    NINO(73),
    ADULTO_MAYOR(76);

    private final int porcentajeTarifa;

    TipoCliente(int porcentajeTarifa) {
        this.porcentajeTarifa = porcentajeTarifa;
    }

    public int getPorcentajeTarifa() {
        return porcentajeTarifa;
    }
}
