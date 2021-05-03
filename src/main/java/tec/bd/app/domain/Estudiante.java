package tec.bd.app.domain;

import java.util.Date;

public class Estudiante implements Entity{

    private int carne;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private int totalCreditos;

    public Estudiante(int carne, String nombre, String apellido, Date fechaNacimiento, int totalCreditos) {
        this.carne = carne;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.totalCreditos = totalCreditos;
    }

    public int getCarne() {
        return carne;
    }

    public void setCarne(int carne) {
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

    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getTotalCreditos() {
        return totalCreditos;
    }

    public void setTotalCreditos(int totalCreditos) {
        this.totalCreditos = totalCreditos;
    }

    @Override
    public Integer getId() {
        return this.carne;
    }

    @Override
    public String toString() {
        return "id " + this.getCarne() + ", nombre " + this.getNombre() + ", apellido " + this.getApellido() + ", fecha nacimiento " + this.getFechaNacimiento() + ", creditos " + this.getTotalCreditos();
    }

}
