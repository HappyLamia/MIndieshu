package com.orion.darmawan.eclinic.Adapter;

public class Voucher {
    String idvoucher;
    String namavoucher;
    String type;
    int nilai;
    int qty;
    String expdate;

    public Voucher() {

    }

    public Voucher(String idvoucher, String namavoucher, String type, int nilai, int qty, String expdate) {
        this.idvoucher = idvoucher;
        this.namavoucher = namavoucher;
        this.type = type;
        this.nilai = nilai;
        this.qty = qty;
        this.expdate = expdate;
    }

    public String getIdvoucher() {
        return idvoucher;
    }

    public void setIdvoucher(String idvoucher) {
        this.idvoucher = idvoucher;
    }

    public String getNamavoucher() {
        return namavoucher;
    }

    public void setNamavoucher(String namavoucher) {
        this.namavoucher = namavoucher;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNilai() {
        return nilai;
    }

    public void setNilai(int nilai) {
        this.nilai = nilai;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }
}
