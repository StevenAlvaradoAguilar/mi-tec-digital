package tec.bd.app.domain;

public class Estudiante {

    private long carne;
    private String nombre;
    private String apellido;
    private int edad;

    public Estudiante(long carne, String nombre, String apellido, int edad) {
        this.carne = carne;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public long getCarne() {
        return carne;
    }

    public void setCarne(long carne) {
        this.carne = carne;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
