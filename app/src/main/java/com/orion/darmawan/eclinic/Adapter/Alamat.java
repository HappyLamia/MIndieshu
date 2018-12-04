package com.orion.darmawan.eclinic.Adapter;

public class Alamat {
    String alamat,kode_pos,kota,label_alamat,no_telp,penerima;
    String def;

    public Alamat() {
    }

    public Alamat(String alamat, String kode_pos, String kota, String label_alamat, String no_telp, String penerima, String def) {
        this.alamat = alamat;
        this.kode_pos = kode_pos;
        this.kota = kota;
        this.label_alamat = label_alamat;
        this.no_telp = no_telp;
        this.penerima = penerima;
        this.def = def;
    }
}
