package com.orion.darmawan.eclinic.Adapter;

public class Bank {

    private String code;
    private String name;


    public Bank() {

    }
    public Bank(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
