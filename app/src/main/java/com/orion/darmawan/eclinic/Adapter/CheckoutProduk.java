package com.orion.darmawan.eclinic.Adapter;

public class CheckoutProduk {
    private String nama_produk;
    private int qty_beli;
    private double harga_produk;

    public CheckoutProduk(String nama_produk, int qty_beli, double harga_produk) {
        this.nama_produk = nama_produk;
        this.qty_beli = qty_beli;
        this.harga_produk = harga_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public int getQty_beli() {
        return qty_beli;
    }

    public double getHarga_produk() {
        return harga_produk;
    }
}
