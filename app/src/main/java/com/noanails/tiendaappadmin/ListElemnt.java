package com.noanails.tiendaappadmin;

public class ListElemnt {
    private String nombre, ap1, ap2, nTelf, email, idUsuario;

    public ListElemnt(String nombre, String ap1, String ap2, String nTelf, String email, String idUsuario) {
        this.nombre = nombre;
        this.ap1 = ap1;
        this.ap2 = ap2;
        this.nTelf = nTelf;
        this.email = email;
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAp1() {
        return ap1;
    }

    public void setAp1(String ap1) {
        this.ap1 = ap1;
    }

    public String getAp2() {
        return ap2;
    }

    public void setAp2(String ap2) {
        this.ap2 = ap2;
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

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
