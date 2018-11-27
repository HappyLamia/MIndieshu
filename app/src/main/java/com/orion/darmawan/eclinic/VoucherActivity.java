package com.orion.darmawan.eclinic;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    Button btn_claim;
    private VoucherAdapter voucheradapter;
    private FirebaseAuth auth;
    List<Voucher> voucherList;
    RecyclerView recyclerView;
    int vStat = 0;
    String vIdVoucher = "",vIdPasien="",vIdMember="",vMsg="";

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
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        loadVoucher(userData.getId(),user.getUid());
    }

    public String getIntentMsg(){
        String IntentMsgID = "";
        if (getIntent().hasExtra("VoucherID")){
            IntentMsgID = getIntent().getStringExtra("VoucherID");
        }

        return IntentMsgID;
    }

    private void loadVoucher(final String val,final String val2) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,  ServerApi.URL_VOUCHER,
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
                                        voucher.getString("exp_date"),
                                        voucher.getString("status")
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
                params.put("id_member", val2);
                return params;
            }
        };
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void claimVoucher(final String val) {
        final ModelData userData = SharedPrefManager.getInstance(this).getUser();
        final String id_pasien;
        if (userData.getId()==null){
            id_pasien = "";
        }
        else{
            id_pasien = userData.getId();
        }
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,  ServerApi.URL_CLAIM_VOUCHER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            vStat = obj.getInt("status");
                            vMsg = obj.getString("message");
                            if (vStat==1){
                                Toast.makeText(getApplicationContext(), "Voucher Telah Diclaim", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), VoucherActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(), vMsg, Toast.LENGTH_SHORT).show();
                            }

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
                params.put("id_pasien", id_pasien);
                params.put("id_member", user.getUid());
                params.put("id_voucher",val);
                return params;
            }
        };
        //adding our stringrequest to queue
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}
