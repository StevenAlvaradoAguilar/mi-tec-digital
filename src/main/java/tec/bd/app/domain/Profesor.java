package tec.bd.app.domain;

public class Profesor implements Entity{

    private Integer id;
    private String nombre;
    private String apellido;
    private String ciudad;

    public Profesor(int id, String nombre, String apellido, String ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ciudad = ciudad;
    }


    public Integer getId() {
        return this.id;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "id " + this.getId() + ", nombre " + this.getNombre() + ", apellido " + this.getApellido() + ", ciudad " + this.getCiudad();
    }

}
