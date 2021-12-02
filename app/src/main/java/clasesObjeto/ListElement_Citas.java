package clasesObjeto;

//Clase que se encarga de crear los elementos que se van a utilizar para pasar la fecha y hora.

public class ListElement_Citas {
    private String servicio,fecha,hora,idCita,nombre,nTelf,email;;

    public ListElement_Citas(String servicio, String fecha, String hora, String idCita, String nombre, String nTelf, String email) {
        this.servicio = servicio;
        this.fecha = fecha;
        this.hora = hora;
        this.idCita = idCita;
        this.nombre = nombre;
        this.nTelf = nTelf;
        this.email = email;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getnTelf() {
        return nTelf;
    }

    public void setnTelf(String nTelf) {
        this.nTelf = nTelf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

