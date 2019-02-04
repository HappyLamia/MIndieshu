package com.orion.darmawan.eclinic.Adapter;

public class Alamat {
    public String keyId;
    public String defKey;
    public String alamat,kode_pos,kota,label_alamat,no_telp,penerima;
    public double lat,lng;
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

    public Alamat(String alamat, String kode_pos, String kota, String label_alamat, double lat, double lng , String no_telp, String penerima, String def) {
        this.alamat = alamat;
        this.kode_pos = kode_pos;
        this.kota = kota;
        this.label_alamat = label_alamat;
        this.lat = lat;
        this.lng = lng;
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

    public String getDefKey() {
        return defKey;
    }

    public void setDefKey(String defKey) {
        this.defKey = defKey;
    }
}
