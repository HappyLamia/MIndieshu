package com.orion.darmawan.eclinic.Adapter;

public class Product {

    private String id;
    private String nama;
    private int qty;
    private int harga;

    public Product(String id, String nama, int qty, int harga) {
        this.id = id;
        this.nama = nama;
        this.qty = qty;
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

    public int getHarga() {
        return harga;
    }
}
