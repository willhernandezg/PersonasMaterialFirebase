package me.willhernandezg.personasmaterial;

/**
 * Created by android on 07/10/2017.
 */

public class Persona {
    private int foto;
    private String id;
    private String cedula;
    private String nombre;
    private String apellido;
    private int sexo;

    public Persona(){

    }

    public Persona(String id, int foto, String cedula, String nombre, String apellido, int sexo){
        this.id = id;
        this.foto = foto;
        this.cedula=cedula;
        this.nombre=nombre;
        this.apellido=apellido;
        this.sexo=sexo;
    }

    public Persona(int foto, String cedula, String nombre, String apellido, int sexo){
        this.foto = foto;
        this.cedula=cedula;
        this.nombre=nombre;
        this.apellido=apellido;
        this.sexo=sexo;
    }

    public Persona(String cedula, String nombre, String apellido, int sexo){
        this.cedula=cedula;
        this.nombre=nombre;
        this.apellido=apellido;
        this.sexo=sexo;
    }

    public Persona(int foto, String nombre, String apellido){
        this.foto = foto;
        this.cedula="";
        this.nombre=nombre;
        this.apellido=apellido;
        this.sexo=0;
    }

    public Persona(String id){
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    public void guardar(){
        Datos.guardarPersona(this);
    }

    public void editar(){
        Datos.editarPersona(this);
    }

    public void eliminar(){
        Datos.eliminarPersona(this);
    }
}
