package com.orion.darmawan.eclinic.Adapter;

public class Alamat {
    public String keyId;
    public String alamat,kode_pos,kota,label_alamat,no_telp,penerima;
    public String def;

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

    public Alamat(String def) {
        this.def = def;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getKode_pos() {
        return kode_pos;
    }

    public String getKota() {
        return kota;
    }

    public String getLabel_alamat() {
        return label_alamat;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public String getPenerima() {
        return penerima;
    }

    public String getDef() {
        return def;
    }
}
