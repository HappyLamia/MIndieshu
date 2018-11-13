package com.orion.darmawan.eclinic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orion.darmawan.eclinic.Adapter.Bank;
import com.orion.darmawan.eclinic.Adapter.BanksAdapter;
import com.orion.darmawan.eclinic.Adapter.Rekening;
import com.orion.darmawan.eclinic.Model.ModelData;
import com.orion.darmawan.eclinic.Util.ServerApi;
import com.orion.darmawan.eclinic.Util.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RekeningActivity extends Activity {

    private FirebaseDatabase getDatabase;
    private DatabaseReference getRefenence;
    private TextView GetBank, GetName, GetRek;
    //listview object
    ListView listView;

    //the hero list where we will store all the hero objects after parsing json
    List<Bank> bankList;
    EditText inputSearch;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rekening_list);

        GetBank = findViewById(R.id.getBank);
        GetName = findViewById(R.id.getName);
        GetRek = findViewById(R.id.getRek);

        getDatabase = FirebaseDatabase.getInstance();
        getRefenence = getDatabase.getReference();
        ModelData userData = SharedPrefManager.getInstance(this).getUser();

        /*
         * Mendapatkan referensi dari lokasi Admin dan tutunannya yaitu User ID dari masing-masing
         * akun user dan menambahkan ChildListener untuk menangani kejadian saat data ditambahkan,
         * diubah, dihapus dan dialihkan.
         */
        getRefenence.child("Rekening").child(userData.getId()).addChildEventListener(new ChildEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Mengambil daftar item dari database, setiap kali ada turunannya
                Rekening rekening = dataSnapshot.getValue(Rekening.class);
                GetBank.setText("Bank : "+rekening.getBank());
                GetName.setText("Name : "+rekening.getName());
                GetRek.setText("No. Rekening : "+rekening.getNoRek());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //......
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //......
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //.....
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Digunakan untuk menangani kejadian Error
                Log.e("MyListData", "Error: ", databaseError.toException());
            }
        });
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RekeningActivity.this, AtmActivity.class));
                finish();
            }
        });
    }
}