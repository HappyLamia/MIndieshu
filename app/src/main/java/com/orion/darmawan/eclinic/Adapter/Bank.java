package com.orion.darmawan.eclinic.Adapter;

public class Bank {

    private String code;
    private String name;

    public Bank(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
