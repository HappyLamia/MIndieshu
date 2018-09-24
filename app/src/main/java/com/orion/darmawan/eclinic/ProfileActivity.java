package com.orion.darmawan.eclinic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orion.darmawan.eclinic.Model.ModelData;
import com.orion.darmawan.eclinic.Util.SharedPrefManager;


public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView nama,email,phone,gender,birthday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_profile);
        nama = (TextView) findViewById(R.id.nama_profile);
        email = (TextView) findViewById(R.id.emails);
        gender = (TextView) findViewById(R.id.gender);
        phone = (TextView) findViewById(R.id.phone_number);
        birthday = (TextView) findViewById(R.id.birthday);
        ModelData userData = SharedPrefManager.getInstance(this).getUser();
        FirebaseUser user = auth.getCurrentUser();
        nama.setText(userData.getName());
        email.setText(user.getEmail());
        gender.setText(userData.getGender());
    }
}
