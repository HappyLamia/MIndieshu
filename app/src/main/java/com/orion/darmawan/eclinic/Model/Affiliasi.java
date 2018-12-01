package com.orion.darmawan.eclinic.Model;

public class Affiliasi {
    String idpasien;
    String level;

    public Affiliasi() {
    }

    public Affiliasi(String idpasien, String level) {
        this.idpasien = idpasien;
        this.level = level;
    }

    public String getIdpasien() {
        return idpasien;
    }

    public void setIdpasien(String idpasien) {
        this.idpasien = idpasien;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
