package com.orion.darmawan.eclinic.Util;

public class ServerApi {
    public static final String URL_PARENT = "https://demo.indieshu.net/";


    // METHOD POST
    public static final String URL_SYNCH = URL_PARENT+"api/synch/user";
    public static final String URL_OBAT_DOKTER = URL_PARENT+"api/data/wishlist";
        // CART
    public static final String URL_CART = URL_PARENT+"api/cart/add-cart";
        // PARAM (id_member,id_barang,qty,harga)
    public static final String URL_PLUS_CART = URL_PARENT+"api/cart/update-cart/plus";
        // PARAM (id_member,id_barang,qty)
    public static final String URL_MINUS_CART = URL_PARENT+"api/cart/update-cart/minus";
        // PARAM (id_member,id_barang,qty)
    public static final String URL_REMOVE_CART = URL_PARENT+"api/cart/remove-cart";
        // PARAM (id_member,id_barang)
    public static final String URL_CANCEL_CART = URL_PARENT+"api/cart/cancel-cart";
        // PARAM (id_member)
    public static final String URL_CHECKOUT = URL_PARENT+"api/cart/checkout";
        // PARAM (id_member)
        // END CART
    public static final String URL_TOP_UP = URL_PARENT+"api/coin/top-up";
        // PARAM (id_member,saldo,harga)

    // METHOD GET NO PARAM
    public static final String URL_PRODUCT = URL_PARENT+"api/data/barang";
    public static final String URL_BANK = URL_PARENT+"api/data/bank";
    public static final String URL_COIN = URL_PARENT+"api/data/coin";

    // METHOD GET WITH PARAM
    public static final String URL_DETAIL_PRODUCT = URL_PARENT+"api/data/get-detail-barang";
        // PARAM (id_barang)
        // CART
    public static final String URL_GET_CART = URL_PARENT+"api/cart/get-cart";
        // PARAM (id_member)
        // END CART
}