package com.orion.darmawan.eclinic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentMethodActivity extends AppCompatActivity {

    private FirebaseDatabase getDatabase;
    private DatabaseReference getRefenence,noRef;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String namaBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
    }

    public void btShowMapClick(View v){
        Intent i = new Intent(this,MapsActivity.class);
        startActivity(i);
    }
}
