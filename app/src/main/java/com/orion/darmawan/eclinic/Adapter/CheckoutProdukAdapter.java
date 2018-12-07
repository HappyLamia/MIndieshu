package com.orion.darmawan.eclinic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.orion.darmawan.eclinic.R;
import java.util.List;

public class CheckoutProdukAdapter extends RecyclerView.Adapter<CheckoutProdukAdapter.MyViewHolder> {

    private Context mContext;
    private List<CheckoutProduk> CheckoutProdukList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView NamaProduk, Harga, QtyBeli;
        public ImageView thumbnail ;
        public LinearLayout parentLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            NamaProduk = itemView.findViewById(R.id.tvCheckoutCard_NamaProduk);
            Harga = itemView.findViewById(R.id.tvCheckoutCard_HargaProduk);
            QtyBeli = itemView.findViewById(R.id.tvCheckoutCard_QtyBeli);
            thumbnail = itemView.findViewById(R.id.img_thumbnail_checkout);
            parentLayout = itemView.findViewById(R.id.parent_layout_checkout);
        }
    }

    public CheckoutProdukAdapter(Context mContext, List<CheckoutProduk> CheckoutProdukList) {
        this.mContext = mContext;
        this.CheckoutProdukList = CheckoutProdukList;
    }

    @Override
    public CheckoutProdukAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checkout_card, parent, false);
        CheckoutProdukAdapter.MyViewHolder holder = new CheckoutProdukAdapter.MyViewHolder(itemView);
        return new CheckoutProdukAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CheckoutProdukAdapter.MyViewHolder holder, int position) {
        final CheckoutProduk checkoutProduk = CheckoutProdukList.get(position);

        holder.NamaProduk.setText(checkoutProduk.getNama_produk());
        holder.QtyBeli.setText("Qty : "+String.valueOf(checkoutProduk.getQty_beli()));
        holder.Harga.setText(String.valueOf(checkoutProduk.getHarga_produk()));
    }

    @Override
    public int getItemCount() {
        return CheckoutProdukList.size();
    }
}
