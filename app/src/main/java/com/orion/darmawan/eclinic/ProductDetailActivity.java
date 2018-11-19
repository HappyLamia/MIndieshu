package com.orion.darmawan.eclinic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.orion.darmawan.eclinic.Model.ModelData;
import com.orion.darmawan.eclinic.Util.ServerApi;
import com.orion.darmawan.eclinic.Util.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class ProductDetailActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    RequestQueue queue;
    TextView tvProductName,tvProductPrice, tvDetailProduct, tvAvailQtyProduct;
    ProgressBar progressBar;
    String vID_Barang = "";

    ModelData userData;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        queue =  Volley.newRequestQueue(this);

        tvProductName = findViewById(R.id.tvNamaDetailProd);
        tvProductPrice = findViewById(R.id.tvHargaDetailProd);
        tvDetailProduct = findViewById(R.id.tvDescDetailProd);
        tvAvailQtyProduct = findViewById(R.id.tvAvailQtyProd);
        progressBar = findViewById(R.id.login_progress);

        userData = SharedPrefManager.getInstance(this).getUser();
        user = auth.getCurrentUser();

        progressBar.setVisibility(View.VISIBLE);
        LoadDataDetail(getIntentMsg());
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        progressBar.setVisibility(View.GONE);
//    }

    public String getIntentMsg(){
        String IntentMsgID = "";
        if (getIntent().hasExtra("ProductID")){
            IntentMsgID = getIntent().getStringExtra("ProductID");
        }

        return IntentMsgID;
    }

    public void LoadDataDetail(String IDProduct){
        final StringRequest postRequest = new StringRequest(Request.Method.GET, ServerApi.URL_DETAIL_PRODUCT+"/"+IDProduct,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Status", response);
                        //Toast.makeText(getBaseContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject obj = new JSONObject(response);
                            vID_Barang = obj.getString("id_barang");
                            tvProductName.setText(obj.getString("nama_barang"));
                            tvProductPrice.setText(obj.getString("harga_satuan"));
                            tvDetailProduct.setText(obj.getString("volume")+" "+obj.getString("satuan_volume"));
                            if (obj.getDouble("total_qty")<= 10){
                                tvAvailQtyProduct.setText("Stok hampir habis tersisa < "+obj.getDouble("total_qty"));
                            }else {
                                tvAvailQtyProduct.setText("Stok tersedia tersisa < "+obj.getDouble("total_qty"));
                            }
                            progressBar.setVisibility(View.GONE);

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
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(ProductDetailActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        );
        queue.add(postRequest);
    }

    public void btDetailBuyNow_Click(View v){

    }

    public void btDetailAddtoCart_Click(View v){
        final StringRequest postRequest = new StringRequest(Request.Method.POST, ServerApi.URL_CART,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Status", response);
                        //Toast.makeText(getBaseContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject obj = new JSONObject( response);

                            Toast.makeText(ProductDetailActivity.this,tvProductName.getText().toString()+" Added to Cart",Toast.LENGTH_LONG).show();

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
                        Toast.makeText(ProductDetailActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id_member", userData.getId());
                params.put("id_barang", vID_Barang);
                params.put("qty", "1");
                params.put("harga", tvProductPrice.getText().toString());

                return params;
            }
        };
        queue.add(postRequest);
    }
}
