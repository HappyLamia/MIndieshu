package com.orion.darmawan.eclinic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.orion.darmawan.eclinic.Adapter.Bank;
import com.orion.darmawan.eclinic.Adapter.BanksAdapter;
import com.orion.darmawan.eclinic.Util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RekeningActivity extends Activity {

    //listview object
    ListView listView;

    //the hero list where we will store all the hero objects after parsing json
    List<Bank> bankList;
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekening);

        //initializing listview and hero list
        listView = (ListView) findViewById(R.id.listView);
        bankList = new ArrayList<>();

        //this method will fetch and parse the data
        loadBankList();
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
//                RekeningActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void loadBankList() {
        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET,  ServerApi.URL_BANK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray bankArray = obj.getJSONArray("bank");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < bankArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject bankObject = bankArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                Bank bank = new Bank(
                                        bankObject.getString("code"),
                                        bankObject.getString("name"));

                                //adding the bank to herolist
                                bankList.add(bank);
                            }
                            //creating custom adapter object
                            BanksAdapter adapter = new BanksAdapter(bankList, getApplicationContext());
                            //adding the adapter to listview
                            listView.setAdapter(adapter);

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