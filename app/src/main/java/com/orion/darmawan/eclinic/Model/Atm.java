package com.orion.darmawan.eclinic.Model;

public class Atm {
    public String bank;
    public String name;
    public String no_rek;

    public Atm() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Atm(String bank, String name, String no_rek) {
        this.bank = bank;
        this.name = name;
        this.no_rek = no_rek;
    }
}
