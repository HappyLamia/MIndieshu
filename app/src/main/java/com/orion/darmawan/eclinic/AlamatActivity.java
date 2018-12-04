package com.orion.darmawan.eclinic;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orion.darmawan.eclinic.Adapter.Alamat;
import com.orion.darmawan.eclinic.Adapter.AlamatAdapter;

import java.util.ArrayList;
import java.util.List;

public class AlamatActivity extends AppCompatActivity {

    private FirebaseDatabase getDatabase;
    private DatabaseReference getRefenence;
    private FirebaseAuth auth;
    private FirebaseUser userData;
    private AlamatAdapter almtdapter;
    List<Alamat> alamatList;
    RecyclerView recyclerView;

    private EditText label_alamat,penerima,kota,kode_pos,telp;
    TextView txtAlamat,txtKodePos,txtPenerima,txtTelp,txtKota,txtLabel;
    TextView dflBtn,ubahBtn,hapusBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_alamat);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        alamatList = new ArrayList<>();
        almtdapter = new AlamatAdapter(this,  alamatList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(almtdapter);

        loadAlamat();
    }

    public void loadAlamat(){
        auth = FirebaseAuth.getInstance();
        userData = auth.getCurrentUser();
        getDatabase = FirebaseDatabase.getInstance();
        getRefenence = getDatabase.getReference();
        getRefenence.child("Alamat").child(userData.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Alamat almt = dataSnapshot1.getValue(Alamat.class);
                    alamatList.add(almt);
                }
                AlamatAdapter adapter = new AlamatAdapter(AlamatActivity.this, alamatList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AlamatActivity.this, "Error :"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
