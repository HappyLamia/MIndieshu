package com.orion.darmawan.eclinic.Adapter;

public class Coin {
    private int saldo;
    private int harga;


    public Coin() {

    }
    public Coin(int saldo,int harga) {
        this.harga = harga;
        this.saldo = saldo;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
