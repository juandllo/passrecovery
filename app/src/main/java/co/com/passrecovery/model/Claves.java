package co.com.passrecovery.model;

public class Claves {
    private long _id;
    private String nombreServicio;
    private String nombreUsuario;
    private String pass;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "_id: " + get_id() + "\n" +
                "nombreServicio: " + getNombreServicio() + "\n" +
                "pass: " + getPass() + "\n" +
                "nombreUsuario: " + getNombreUsuario();
    }
}
