package tec.bd.app.domain;

import java.util.Objects;

public class Estudiante implements Comparable<Estudiante>{

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

    @Override
    public boolean equals(Object estudiante) {
        if (this == estudiante)
            return true;
        if (estudiante == null || getClass() != estudiante.getClass())
            return false;
        Estudiante that = (Estudiante) estudiante;
        return carne == that.carne && edad == that.edad && nombre.equals(that.nombre) && apellido.equals(that.apellido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carne, nombre, apellido, edad);
    }

    @Override
    public int compareTo(Estudiante estudiante) {
        return (int) (this.carne - estudiante.getCarne());
    }
}
