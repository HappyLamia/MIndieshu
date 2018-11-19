package com.orion.darmawan.eclinic;

public class Cart {
    private String id;
    private String nama;
    private int qty;
    private int qtySisa;
    private int harga;

    public Cart(String id, String nama, int qty, int qtySisa, int harga) {
        this.id = id;
        this.nama = nama;
        this.qty = qty;
        this.qtySisa = qtySisa;
        this.harga = harga;
    }
    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getQty() {
        return qty;
    }

    public int getQtySisa() {
        return qtySisa;
    }

    public int getHarga() {
        return harga;
    }
}
