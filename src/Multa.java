public class Multa {
    private Usuario usuario;
    private double valorDeMulta;
    private String tipoDeMulta;

    public Multa(Usuario usuario, double valorDeMulta, String tipoDeMulta) {
        this.usuario = usuario;
        this.valorDeMulta = valorDeMulta;
        this.tipoDeMulta = tipoDeMulta;
        usuario.tieneMulta();
    }
    // Getter para obtener el usuario asociado con la multa
    public Usuario getUsuario() {
        return usuario;
    }
}
