public class Usuario {
    private String nombre;
    private String apellido;
    private String cedula;
    private int edad;
    private String direccion;
    private boolean tieneMulta = false;

    public Usuario(String nombre, String apellido, String cedula, int edad, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.edad = edad;
        this.direccion = direccion;
    }

    public boolean tieneMulta() {
        return tieneMulta;
    }

    public String getCedula() {
        return cedula;
    }
    public String obtenerDatosDelUsuario() {
        return "Usuario: " + nombre + " " + apellido + ", Cédula: " + cedula + ", Edad: " + edad + ", Dirección: " + direccion + ", Multa: " + (tieneMulta ? "Sí" : "No");
    }

    public void setTieneMulta(boolean tieneMulta) {
        this.tieneMulta = tieneMulta;
    }
}
