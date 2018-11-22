package com.orion.darmawan.eclinic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orion.darmawan.eclinic.ProductDetailActivity;
import com.orion.darmawan.eclinic.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context mContext;
    private List<Cart> CartList;

    public CartAdapter(Context mContext, List<Cart> CartList) {
        this.mContext = mContext;
        this.CartList = CartList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Harga, QtyBeli, QtySisa;
        public CheckBox NamaProduct;
        public ImageView thumbnail ;
        public LinearLayout parentLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            NamaProduct = itemView.findViewById(R.id.cbCart_NamaProduct);
            Harga = itemView.findViewById(R.id.tvCartCard_Harga);
            QtyBeli = itemView.findViewById(R.id.tvCart_QtyBeli);
            QtySisa = itemView.findViewById(R.id.tvCartCard_SisaQty);
            thumbnail = itemView.findViewById(R.id.imgCart_ImgProduct);
            //overflow = itemView.findViewById(R.id.overflow);
            parentLayout = itemView.findViewById(R.id.parent_layout_cart);
        }
    }

    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_card, parent, false);
        CartAdapter.MyViewHolder holder = new CartAdapter.MyViewHolder(itemView);
        return new CartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartAdapter.MyViewHolder holder, int position) {
        final Cart cart = CartList.get(position);

        holder.NamaProduct.setText(cart.getNama());
        holder.Harga.setText(String.valueOf(cart.getHarga()));
        holder.QtyBeli.setText(String.valueOf(cart.getQty()));
        holder.QtySisa.setText(String.valueOf(cart.getQtySisa()));
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,ProductDetailActivity.class);
                i.putExtra("ProductID",cart.getId());
                mContext.startActivity(i);
                Toast.makeText(mContext, cart.getNama(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return CartList.size();
    }
}
