package uniquindio.edu.co.model;

public class Asiento {

    private String codigo;
    private Character fila;
    private int numero;
    private EstadoAsiento estado;


    public Asiento(String codigo, Character fila, int numero, EstadoAsiento estado) {
        this.codigo = codigo;
        this.fila = fila;
        this.numero = numero;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Character getFila() {
        return fila;
    }

    public void setFila(Character fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public EstadoAsiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsiento estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "----------Asiento---------{" +
                "codigo:'" + codigo + '\'' +
                ", fila:" + fila +
                ", numero:" + numero +
                ", estado:" + estado;
    }
}
