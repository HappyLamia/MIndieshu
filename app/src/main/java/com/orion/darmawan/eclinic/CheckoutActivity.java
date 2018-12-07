package com.orion.darmawan.eclinic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orion.darmawan.eclinic.Adapter.Alamat;
import com.orion.darmawan.eclinic.Adapter.AlamatAdapter;
import com.orion.darmawan.eclinic.Adapter.Cart;
import com.orion.darmawan.eclinic.Adapter.CartAdapter;
import com.orion.darmawan.eclinic.Adapter.CheckoutProduk;
import com.orion.darmawan.eclinic.Adapter.CheckoutProdukAdapter;
import com.orion.darmawan.eclinic.Model.ModelData;
import com.orion.darmawan.eclinic.Util.ServerApi;
import com.orion.darmawan.eclinic.Util.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private List<CheckoutProduk> checkoutProdukList = new ArrayList<>();
    private List<Alamat> alamatList = new ArrayList<>();

    private RecyclerView recyclerView;
    private CheckoutProdukAdapter checkoutProdukAdapter;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseUser userData;
    private FirebaseDatabase getDatabase;
    private DatabaseReference getRefenence,noRef;
    TextView tvHargaTotal_Checkout;
    Double vHargaTotalProduk = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        recyclerView = findViewById(R.id.recycler_view_checkout);
        tvHargaTotal_Checkout = findViewById(R.id.tvHargaTotal_checkout);

        checkoutProdukAdapter = new CheckoutProdukAdapter(this, checkoutProdukList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(checkoutProdukAdapter);

        FillSpinnEkspedisi();
        LoadAddressUser();
        LoadCheckoutProduk();
    }

    private void LoadCheckoutProduk(){
        ModelData userData = SharedPrefManager.getInstance(this).getUser();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,  ServerApi.URL_GET_CART+"/"+userData.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            //converting the string to json array object
                            JSONArray array = obj.getJSONArray("cart");

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject checkoutproduk = array.getJSONObject(i);

//                                adding the product to product list
                                checkoutProdukList.add(new CheckoutProduk("Barang "+i,
                                        checkoutproduk.getInt("qty"),
                                        Double.valueOf(checkoutproduk.getDouble("harga"))));

                                vHargaTotalProduk += checkoutproduk.getDouble("harga");
                            }

                            tvHargaTotal_Checkout.setText(vHargaTotalProduk.toString());

                            //creating adapter object and setting it to recyclerview
                            CheckoutProdukAdapter adapt = new CheckoutProdukAdapter(CheckoutActivity.this, checkoutProdukList);
                            recyclerView.setAdapter(adapt);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void LoadAddressUser(){
        auth = FirebaseAuth.getInstance();
        userData = auth.getCurrentUser();
        getDatabase = FirebaseDatabase.getInstance();
        getRefenence = getDatabase.getReference();
        getRefenence.child("Alamat").child(userData.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Alamat almt = dataSnapshot1.getValue(Alamat.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CheckoutActivity.this, "Error :"+databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void FillSpinnEkspedisi(){

    }

    public void btCheckoutPayment_Click(View v){
        Intent i = new Intent(this, PaymentMethodActivity.class);
        startActivity(i);
        finish();
    }
}
