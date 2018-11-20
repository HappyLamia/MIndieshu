package com.orion.darmawan.eclinic.Model;

public class User {
    public String namauser;
    public String jk;
    public String tgl_lahir;
    public String no_hp;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String namauser, String jk, String tgl_lahir, String no_hp) {
        this.namauser = namauser;
        this.jk = jk;
        this.tgl_lahir = tgl_lahir;
        this.no_hp = no_hp;
    }
}
