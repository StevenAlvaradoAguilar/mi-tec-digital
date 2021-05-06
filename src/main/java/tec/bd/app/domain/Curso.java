package tec.bd.app.domain;

import java.util.Date;

public class Curso implements Entity{

    private int id;
    private String nombre;
    private String departamento;
    private int creditos;

    public Curso(int id, String nombre, String departamento, int creditos) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.creditos = creditos;
    }

    public Curso() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }


    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id " + this.getId() + ", nombre " + this.getNombre() + ", departamento " + this.getDepartamento() + ", créditos " + this.getCreditos();
    }

}
