package com.orion.darmawan.eclinic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orion.darmawan.eclinic.Adapter.Cart;
import com.orion.darmawan.eclinic.Adapter.CartAdapter;
import com.orion.darmawan.eclinic.Model.ModelData;
import com.orion.darmawan.eclinic.Util.ServerApi;
import com.orion.darmawan.eclinic.Util.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private List<Cart> cartList;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        recyclerView = findViewById(R.id.recycler_view_cart);

        cartList = new ArrayList<>();
        cartAdapter = new CartAdapter(this, cartList);
        recyclerView.setAdapter(cartAdapter);
        loadCart();
    }

    private void loadCart() {
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
                                JSONObject cart = array.getJSONObject(i);

//                                adding the product to product list
                                cartList.add(new Cart(
                                        cart.getString("id_barang"),
                                        "Barang "+i,
                                        cart.getInt("qty"),
                                        0,
                                        cart.getInt("harga")
                                        ));
                            }

                            //creating adapter object and setting it to recyclerview
                            CartAdapter adapt = new CartAdapter(CartActivity.this, cartList);
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
}
