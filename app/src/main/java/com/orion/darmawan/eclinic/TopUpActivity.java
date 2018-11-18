package com.orion.darmawan.eclinic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.text.Text;
import com.orion.darmawan.eclinic.Model.ModelData;
import com.orion.darmawan.eclinic.Util.ServerApi;
import com.orion.darmawan.eclinic.Util.SharedPrefManager;
import com.orion.darmawan.eclinic.Util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TopUpActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    //Declaring an Spinner
    private Spinner spinner;

    //An ArrayList for Spinner Items
    private ArrayList<String> coin;

    //JSON Array
    private JSONArray result;
    private TextView hargaTxt;
    private int var_saldo,var_harga;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        hargaTxt = (TextView) findViewById(R.id.hargaTxt);
        btnAdd = (Button) findViewById(R.id.add_btn);

        coin = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        getData();

        final ModelData userData = SharedPrefManager.getInstance(this).getUser();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_member = userData.getId();
                int saldo = var_saldo;
                int harga = var_harga;

                topUp(id_member,saldo,harga);
            }
        });
    }

    private void topUp(String val1,int val2,int val3) {
        //first getting the values
        final String id_member = val1 ;
        final int saldo = val2;
        final int harga = val3;

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TOP_UP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), TopUpActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_member", id_member);
                params.put("saldo", String.valueOf(saldo));
                params.put("harga", String.valueOf(harga));
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET,  ServerApi.URL_COIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray("coin");

                            //Calling method getStudents to get the students from the JSON Array
                            getListSaldo(result);
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

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getListSaldo(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                coin.add(json.getString("saldo"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<String>(TopUpActivity.this, android.R.layout.simple_spinner_dropdown_item, coin));
    }

    //Method to get student name of a particular position
    private int getSaldo(int position){
        int saldo=0;
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            saldo = json.getInt("saldo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return saldo;
    }

    private int getHarga(int position){
        int harga=0;
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            harga = json.getInt("harga");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return harga;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        hargaTxt.setText(formatRupiah.format((double)getHarga(i)));
        var_saldo = getSaldo(i);
        var_harga = getHarga(i);
//        String priceTxt = "Harga : Rp. "+getHarga(i);
//        hargaTxt.setText(priceTxt);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
