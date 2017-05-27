package com.jgutierrez.jsparking;

/**
 * Created by jgutierrez on 27/05/2017.
 */

class Usuario {
    private String Nombre;
    private String Telefono;
    private String Email;
    private String Uid;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

}
