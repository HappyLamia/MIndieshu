package com.orion.darmawan.eclinic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orion.darmawan.eclinic.CartActivity;
import com.orion.darmawan.eclinic.ProductDetailActivity;
import com.orion.darmawan.eclinic.R;
import com.orion.darmawan.eclinic.Util.ServerApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context mContext;
    private List<Cart> CartList;
    RequestQueue queue;
    private FirebaseAuth auth;
    private FirebaseUser user;
    int vStatus = 0;
    String vMsg = "";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Harga, QtyBeli, QtySisa;
        public CheckBox NamaProduct;
        public ImageView thumbnail, imgMinus, imgPlus, imgRemove ;
        public LinearLayout parentLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            NamaProduct = itemView.findViewById(R.id.cbCart_NamaProduct);
            Harga = itemView.findViewById(R.id.tvCartCard_Harga);
            QtyBeli = itemView.findViewById(R.id.tvCart_QtyBeli);
            QtySisa = itemView.findViewById(R.id.tvCartCard_SisaQty);
            thumbnail = itemView.findViewById(R.id.imgCart_ImgProduct);
            parentLayout = itemView.findViewById(R.id.parent_layout_cart);
            imgMinus = itemView.findViewById(R.id.img_cart_minus);
            imgPlus = itemView.findViewById(R.id.img_cart_plus);
            imgRemove = itemView.findViewById(R.id.imgCart_Delete);
        }
    }

    public CartAdapter(Context mContext, List<Cart> CartList) {
        this.mContext = mContext;
        this.CartList = CartList;
    }

    public void removeAt(int position) {
        CartList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, CartList.size());
    }

    public void onRemoveItemCart(final String id_member, final String id_Barang){
        final StringRequest postRequest = new StringRequest(Request.Method.POST, ServerApi.URL_REMOVE_CART,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Status", response);
                        //Toast.makeText(getBaseContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject obj = new JSONObject( response);
//                            vStatus = obj.getInt("status");
//                            vMsg = obj.getString("message");
//                            if (vStatus==1){
//                                Toast toast = Toast.makeText(mContext, vMsg, Toast.LENGTH_SHORT);
//                                toast.show();
//                            }
//                            Toast.makeText(dProductDetailActivity.this,tvProductName.getText().toString()+" Added to Cart",Toast.LENGTH_LONG).show();

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
//                        Toast.makeText(ProductDetailActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id_member", id_member);
                params.put("id_barang", id_Barang);

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void onDecreaseQtyCart(final String id_member, final String id_Barang, final String qty_beli){
        final StringRequest postRequest = new StringRequest(Request.Method.POST, ServerApi.URL_MINUS_CART,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Status", response);
                        //Toast.makeText(getBaseContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject obj = new JSONObject( response);
//                            vStatus = obj.getInt("status");
//                            vMsg = obj.getString("message");
//                            if (vStatus==1){
//                                Toast toast = Toast.makeText(mContext, vMsg, Toast.LENGTH_SHORT);
//                                toast.show();
//                            }
//                            Toast.makeText(dProductDetailActivity.this,tvProductName.getText().toString()+" Added to Cart",Toast.LENGTH_LONG).show();

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
//                        Toast.makeText(ProductDetailActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id_member", id_member);
                params.put("id_barang", id_Barang);
                params.put("qty",qty_beli);

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void onIncreaseQtyCart(final String id_member, final String id_Barang, final String qty_beli){
        final StringRequest postRequest = new StringRequest(Request.Method.POST, ServerApi.URL_PLUS_CART,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Status", response);
                        //Toast.makeText(getBaseContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject obj = new JSONObject( response);
//                            vStatus = obj.getInt("status");
//                            vMsg = obj.getString("message");
//                            if (vStatus==1){
//                                Toast toast = Toast.makeText(mContext, vMsg, Toast.LENGTH_SHORT);
//                                toast.show();
//                            }
//                            Toast.makeText(dProductDetailActivity.this,tvProductName.getText().toString()+" Added to Cart",Toast.LENGTH_LONG).show();

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
//                        Toast.makeText(ProductDetailActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id_member", id_member);
                params.put("id_barang", id_Barang);
                params.put("qty",qty_beli);

                return params;
            }
        };
        queue.add(postRequest);
    }


    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_card, parent, false);
        CartAdapter.MyViewHolder holder = new CartAdapter.MyViewHolder(itemView);
        queue =  Volley.newRequestQueue(mContext);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        return new CartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartAdapter.MyViewHolder holder, final int position) {
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
        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRemoveItemCart(user.getUid(),cart.getId());
                removeAt(position);
            }
        });
        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.QtyBeli.setText(String.valueOf(
                        onIncreaseQty(
                                Integer.valueOf(holder.QtyBeli.getText().toString()),
                                user.getUid(),
                                cart.getId())
                ));
            }
        });
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.QtyBeli.setText(String.valueOf(
                        onDecreaseQty(Integer.valueOf(holder.QtyBeli.getText().toString()),
                                user.getUid(),
                                cart.getId())
                ));
            }
        });
    }

    private Integer onIncreaseQty(Integer vQtyBeli, String id_member, String id_barang) {
        Integer vRet = vQtyBeli;
        vRet = vRet+1;

//        onIncreaseQtyCart(id_member,id_barang,String.valueOf(vRet));

        return vRet;
    }

    private Integer onDecreaseQty(Integer vQtyBeli, String id_member, String id_barang) {
        Integer vRet = vQtyBeli;

        if (vRet!=1){
            vRet = vRet-1;
//            onDecreaseQtyCart(id_member,id_barang,String.valueOf(vRet));
        }

        return vRet;
    }

    @Override
    public int getItemCount() {
        return CartList.size();
    }
}
