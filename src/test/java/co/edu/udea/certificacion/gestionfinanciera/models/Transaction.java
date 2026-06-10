package co.edu.udea.certificacion.gestionfinanciera.models;

public class Transaction {
    private final String tipo;
    private final String monto;
    private final String categoria;
    private final String fecha;

    public Transaction(String tipo, String monto, String categoria, String fecha) {
        this.tipo = tipo;
        this.monto = monto;
        this.categoria = categoria;
        this.fecha = fecha;
    }


    public String getTipo() { return tipo; }
    public String getMonto() { return monto; }
    public String getCategoria() { return categoria; }
    public String getFecha() { return fecha; }
}
