package co.edu.udea.certificacion.gestionfinanciera.models;

public class Category {

    private String nombre;

    public Category() {
    }

    public Category(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
