package com.orion.darmawan.eclinic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orion.darmawan.eclinic.Adapter.AlamatAdapter;
import com.orion.darmawan.eclinic.Adapter.AtmAdapter;
import com.orion.darmawan.eclinic.Adapter.Bank;
import com.orion.darmawan.eclinic.Adapter.Atm;


public class RekeningActivity extends Activity {

    private FirebaseDatabase getDatabase;
    private DatabaseReference getRefenence;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private TextView GetBank, GetName, GetRek;

    private AtmAdapter atmAdapter;
    List<Atm> atmList;
    RecyclerView recyclerView;

    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekening);

        auth = FirebaseAuth.getInstance();
        getDatabase = FirebaseDatabase.getInstance();
        getRefenence = getDatabase.getReference();
        user = auth.getCurrentUser();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        atmList = new ArrayList<>();
        atmAdapter = new AtmAdapter(this,atmList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(atmAdapter);

        getRefenence.child("Rekening").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Atm atm = dataSnapshot1.getValue(Atm.class);
                    atmList.add(atm);
                }
                AtmAdapter adapter = new AtmAdapter(RekeningActivity.this, atmList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RekeningActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        btnAdd = (Button) findViewById(R.id.btnAddRekening);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RekeningActivity.this, AtmActivity.class));
                finish();
            }
        });
    }
}