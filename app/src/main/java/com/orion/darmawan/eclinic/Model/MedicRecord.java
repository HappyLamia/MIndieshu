package com.orion.darmawan.eclinic.Model;

public class MedicRecord {
    private String no;
    private String keys;
    private String tgl;

    public MedicRecord(String no, String keys,String tgl) {
        this.no = no;
        this.keys = keys;
        this.tgl = tgl;
    }
    public String getNo() {
        return no;
    }

    public String getKeys() {
        return keys;
    }

    public String getTgl() {
        return tgl;
    }
}