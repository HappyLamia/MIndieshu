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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orion.darmawan.eclinic.Adapter.Atm;
import com.orion.darmawan.eclinic.Model.ModelData;
import com.orion.darmawan.eclinic.Util.ServerApi;
import com.orion.darmawan.eclinic.Util.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AtmActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    private FirebaseDatabase getDatabase;
    private DatabaseReference getRefenence,noRef;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button btnAdd;
    private EditText inputName, inputRek;
    private String namaBank;

    //Declaring an Spinner
    private Spinner spinner;

    //An ArrayList for Spinner Items
    private ArrayList<String> banks;

    //JSON Array
    private JSONArray result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm);

        inputName = (EditText) findViewById(R.id.nama_nasabah);
        inputRek = (EditText) findViewById(R.id.no_rek);

        banks = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);


        getData();

        btnAdd = (Button) findViewById(R.id.add_btn);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bank = namaBank;
                String name = inputName.getText().toString();
                String no_rek = inputRek.getText().toString();
                writeNewRekening(bank,name,no_rek);
            }
        });
    }
    private void writeNewRekening(String bank, String name, String no_rek) {
        getDatabase = FirebaseDatabase.getInstance();
        getRefenence = getDatabase.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        noRef = getDatabase.getReference("Rekening");
        String key = noRef.push().getKey();
        Atm atm = new Atm(bank,name,no_rek);
        getRefenence.child("Rekening").child(user.getUid()).child(key).setValue(atm);
        startActivity(new Intent(AtmActivity.this, RekeningActivity.class));
        finish();
    }

    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET,  ServerApi.URL_BANK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray("bank");

                            //Calling method getStudents to get the students from the JSON Array
                            getBanks(result);
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

    private void getBanks(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                banks.add(json.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<String>(AtmActivity.this, android.R.layout.simple_spinner_dropdown_item, banks));
    }

    //Method to get student name of a particular position
    private String getName(int position){
        String name="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            name = json.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        namaBank = getName(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        namaBank = "";
    }
}
