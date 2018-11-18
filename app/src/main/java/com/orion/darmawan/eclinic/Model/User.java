package com.orion.darmawan.eclinic.Model;

public class User {
    public String namauser;
    public String jk;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String namauser, String jk) {
        this.namauser = namauser;
        this.jk = jk;
    }
}
