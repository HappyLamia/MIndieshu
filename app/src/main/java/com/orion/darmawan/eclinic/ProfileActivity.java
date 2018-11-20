package com.orion.darmawan.eclinic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orion.darmawan.eclinic.Model.ModelData;
import com.orion.darmawan.eclinic.Util.SharedPrefManager;


public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView nama,email,phone,gender,birthday;
    private TextView nav_rekening,nav_topup,nav_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_profile);
//        nama = (TextView) findViewById(R.id.nama_profile);
//        email = (TextView) findViewById(R.id.emails);
//        gender = (TextView) findViewById(R.id.gender);
//        phone = (TextView) findViewById(R.id.phone_number);
//        birthday = (TextView) findViewById(R.id.birthday);
        ModelData userData = SharedPrefManager.getInstance(this).getUser();
        FirebaseUser user = auth.getCurrentUser();
//        nama.setText(userData.getName());
//        email.setText(user.getEmail());
//        gender.setText(userData.getGender());
        nav_rekening = (TextView) findViewById(R.id.nav_rekening);
        nav_topup = (TextView) findViewById(R.id.nav_topup);
        nav_account = (TextView) findViewById(R.id.nav_account);

        nav_rekening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, RekeningActivity.class));
            }
        });
        nav_topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, TopUpActivity.class));
            }
        });
        nav_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }
        });
    }
}
