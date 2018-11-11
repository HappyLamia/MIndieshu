package com.orion.darmawan.eclinic.Adapter;

public class Rekening {
    private String bank;
    private String name;
    private String no_rek;

    //Membuat Method Getter (Wajib), Untuk mendapatkan data NIM, Nama dan Jurusan
    public String getBank() {
        return bank;
    }
    public String getName() {
        return name;
    }
    public String getNoRek() {
        return no_rek;
    }

    //Membuat Konstuktor kosong untuk membaca data snapshot
    public Rekening(){

    }

    public Rekening(String bank, String name, String no_rek) {
        this.bank = bank;
        this.name = name;
        this.no_rek = no_rek;
    }
}
