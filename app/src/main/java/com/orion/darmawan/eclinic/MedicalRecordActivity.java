package com.orion.darmawan.eclinic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.orion.darmawan.eclinic.Data.MedicAdapter;
import com.orion.darmawan.eclinic.Model.MedicRecord;
import com.orion.darmawan.eclinic.Model.ModelData;
import com.orion.darmawan.eclinic.Util.ServerApi;
import com.orion.darmawan.eclinic.Util.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MedicalRecordActivity extends AppCompatActivity {

    ModelData userData = SharedPrefManager.getInstance(this).getUser();
    // List view
    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;
    List<MedicRecord> Mediclist;



    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record);
        // Listview Data

        lv = (ListView) findViewById(R.id.list_view);
        Mediclist = new ArrayList<>();
        loadMedicList();

    }
    private void loadMedicList() {
        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_MEDICAL_RECORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray medicArray = obj.getJSONArray("list");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < medicArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject medicObject = medicArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                MedicRecord medrec = new MedicRecord(
                                        medicObject.getString("id"),
                                        medicObject.getString("keys") ,
                                        medicObject.getString("tgl"));

                                //adding the hero to herolist
                                Mediclist.add(medrec);
                            }

                            //creating custom adapter object
                            MedicAdapter adapter = new MedicAdapter(Mediclist, getApplicationContext());

                            //adding the adapter to listview
                            lv.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                //creating a request queue
                RequestQueue requestQueue = Volley.newRequestQueue(this);

                //adding the string request to request queue
                requestQueue.add(stringRequest);
    }

}
