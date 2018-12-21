package com.orion.darmawan.eclinic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orion.darmawan.eclinic.Adapter.Alamat;
import com.orion.darmawan.eclinic.Adapter.AlamatAdapter;

import java.util.ArrayList;
import java.util.List;

public class AlamatActivity extends AppCompatActivity {

    private FirebaseDatabase getDatabase;
    private DatabaseReference getRefenence,noRef;
    private FirebaseAuth auth;
    private FirebaseUser userData;
    private AlamatAdapter almtdapter;
    List<Alamat> alamatList;
    RecyclerView recyclerView;

    private EditText label_alamat,penerima,kota,kode_pos,telp,alamat,def;
    private TextView txtAlamat,txtKodePos,txtPenerima,txtTelp,txtKota,txtLabel;
    private TextView dflBtn,ubahBtn,hapusBtn;
    private Button btnSubmit,btnAdd;

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

        btnAdd = (Button) findViewById(R.id.btnAddAlamat);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addForm("");
            }
        });

        loadAlamat();
    }

    public void addForm(final String key){
        setContentView(R.layout.activity_alamat);
        label_alamat = (EditText) findViewById(R.id.label_alamat);
        penerima = (EditText) findViewById(R.id.penerima);
        alamat = (EditText) findViewById(R.id.alamat);
        kota = (EditText) findViewById(R.id.kota);
        kode_pos = (EditText) findViewById(R.id.kode_pos);
        telp = (EditText) findViewById(R.id.phone);
        def = (EditText) findViewById(R.id.def);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        if (key.equals("")){
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String inputAlamat = alamat.getText().toString();
                    String inputPenerima = penerima.getText().toString();
                    String inputKodePos = kode_pos.getText().toString();
                    String inputLabelAlamat = label_alamat.getText().toString();
                    String no_telp = telp.getText().toString();
                    String inputKota = kota.getText().toString();
                    addAlamat(inputAlamat,inputKodePos,inputKota,inputLabelAlamat,no_telp,inputPenerima);
                }
            });
        }else{
            btnSubmit.setText("Update Alamat");
            auth = FirebaseAuth.getInstance();
            userData = auth.getCurrentUser();
            getDatabase = FirebaseDatabase.getInstance();
            getRefenence = getDatabase.getReference();
            getRefenence.child("Alamat").child(userData.getUid()).child(key).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Alamat almt = dataSnapshot.getValue(Alamat.class);
                            alamat.setText(almt.alamat);
                            kode_pos.setText(almt.kode_pos);
                            kota.setText(almt.kota);
                            label_alamat.setText(almt.label_alamat);
                            telp.setText(almt.no_telp);
                            penerima.setText(almt.penerima);
                            def.setText(almt.def);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(AlamatActivity.this, "getUser:onCancelled"+databaseError.toException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String inputAlamat = alamat.getText().toString();
                    String inputPenerima = penerima.getText().toString();
                    String inputKodePos = kode_pos.getText().toString();
                    String inputLabelAlamat = label_alamat.getText().toString();
                    String no_telp = telp.getText().toString();
                    String inputKota = kota.getText().toString();
                    String inputDef = def.getText().toString();
                    updateAlamat(key,inputAlamat,inputKodePos,inputKota,inputLabelAlamat,no_telp,inputPenerima,inputDef);
//                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                }
            });
        }



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
                    String key = dataSnapshot1.getKey();
                    almt.setKeyId(key);
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

    public void defaultBtn(final String keyNew){
        auth = FirebaseAuth.getInstance();
        userData = auth.getCurrentUser();
        getDatabase = FirebaseDatabase.getInstance();
        getRefenence = getDatabase.getReference().child("Alamat").child(userData.getUid());
        getRefenence.orderByChild("def").equalTo("TRUE").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Alamat almt = dataSnapshot.getValue(Alamat.class);
                String key = dataSnapshot.getKey();
                Alamat almtOld = new Alamat(almt.alamat,almt.kode_pos,almt.kota,almt.label_alamat,almt.no_telp,almt.penerima,"FALSE");
                getRefenence.child(key).setValue(almtOld);
                Alamat almtNew = new Alamat(almt.alamat,almt.kode_pos,almt.kota,almt.label_alamat,almt.no_telp,almt.penerima,"TRUE");
                getRefenence.child(key).setValue(almtNew);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AlamatActivity.this, "getUser:onCancelled"+databaseError.toException(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(new Intent(AlamatActivity.this, AlamatActivity.class));
        finish();
    }

    public void removeAddress(String key){
        auth = FirebaseAuth.getInstance();
        userData = auth.getCurrentUser();
        getDatabase = FirebaseDatabase.getInstance();
        getRefenence = getDatabase.getReference();
        getRefenence.child("Alamat").child(userData.getUid()).child(key).removeValue();
        startActivity(new Intent(AlamatActivity.this, AlamatActivity.class));
        finish();
    }

    public void addAlamat(String alamat,String kode_pos,String kota,String label_alamat,String no_telp,String penerima){
        auth = FirebaseAuth.getInstance();
        userData = auth.getCurrentUser();
        getDatabase = FirebaseDatabase.getInstance();
        getRefenence = getDatabase.getReference();
        noRef = getDatabase.getReference("Alamat");
        String key = noRef.push().getKey();
        Alamat almt = new Alamat(alamat,kode_pos,kota,label_alamat,no_telp,penerima,"FALSE");
        getRefenence.child("Alamat").child(userData.getUid()).child(key).setValue(almt);
        startActivity(new Intent(AlamatActivity.this, AlamatActivity.class));
        finish();
    }

    public void updateAlamat(String key,String alamat,String kode_pos,String kota,String label_alamat,String no_telp,String penerima,String def){
        auth = FirebaseAuth.getInstance();
        userData = auth.getCurrentUser();
        getDatabase = FirebaseDatabase.getInstance();
        getRefenence = getDatabase.getReference();
        noRef = getDatabase.getReference("Alamat");
        Alamat almt = new Alamat(alamat,kode_pos,kota,label_alamat,no_telp,penerima,def);
        getRefenence.child("Alamat").child(userData.getUid()).child(key).setValue(almt);
        startActivity(new Intent(AlamatActivity.this, AlamatActivity.class));
        finish();
    }
}
