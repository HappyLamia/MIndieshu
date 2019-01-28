package com.orion.darmawan.eclinic.Adapter;

public class Kurir {
    String idKey;
    String name;

    public Kurir() {
    }

    public Kurir(String idKey, String name) {
        this.idKey = idKey;
        this.name = name;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
