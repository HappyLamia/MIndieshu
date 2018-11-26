package com.orion.darmawan.eclinic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.orion.darmawan.eclinic.Adapter.Product;
import com.orion.darmawan.eclinic.Adapter.ProductsAdapter;
import com.orion.darmawan.eclinic.Adapter.Voucher;
import com.orion.darmawan.eclinic.Adapter.VoucherAdapter;
import com.orion.darmawan.eclinic.Model.ModelData;
import com.orion.darmawan.eclinic.Util.ServerApi;
import com.orion.darmawan.eclinic.Util.SharedPrefManager;
import com.orion.darmawan.eclinic.Util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoucherActivity extends AppCompatActivity {

    private VoucherAdapter voucheradapter;
    List<Voucher> voucherList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);
        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        voucherList = new ArrayList<>();
        voucheradapter = new VoucherAdapter(this,  voucherList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(voucheradapter);
        ModelData userData = SharedPrefManager.getInstance(this).getUser();

        loadVoucher(userData.getId());
    }

    private void loadVoucher(final String val) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,  ServerApi.URL_VOUCHER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            //converting the string to json array object
                            JSONArray array = obj.getJSONArray("voucher");

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject voucher = array.getJSONObject(i);

                                //adding the product to product list
                                voucherList.add(new Voucher(
                                        voucher.getString("id_voucher"),
                                        voucher.getString("nama_voucher"),
                                        voucher.getString("type"),
                                        voucher.getInt("nilai"),
                                        voucher.getInt("qty"),
                                        voucher.getString("exp_date")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            VoucherAdapter adapter = new VoucherAdapter(VoucherActivity.this, voucherList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_pasien", val);
                return params;
            }
        };
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
