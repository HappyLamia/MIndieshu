package com.orion.darmawan.eclinic;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orion.darmawan.eclinic.Model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText inputEmail, inputPassword,confirmPassword,inputNama,inputTgl,inputHp;
    private Button btnSignIn, btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseDatabase getDatabase;
    private DatabaseReference getRefenence;
    public String jenis_kelamin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.back_button);
        btnSignUp = (Button) findViewById(R.id.signup_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputNama = (EditText) findViewById(R.id.nama_member);
        inputTgl = (EditText) findViewById(R.id.tgl_lhr);
        inputHp = (EditText) findViewById(R.id.no_hp);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);

        inputTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String con_password = confirmPassword.getText().toString().trim();
                String nama = inputNama.getText().toString();
                String tgl_lahir = inputTgl.getText().toString();
                String no_hp = inputHp.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Email Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!con_password.equals(password)) {
                    Toast.makeText(getApplicationContext(), "Konfirmasi Password Tidak Sama", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(nama)) {
                    Toast.makeText(getApplicationContext(), "Nama Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(tgl_lahir)) {
                    Toast.makeText(getApplicationContext(), "Tanggal Lahir Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(no_hp)) {
                    Toast.makeText(getApplicationContext(), "No HP Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password terlalu pendek!. Minimal 6 karakter", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                createUser(email,password);
            }
        });
    }

    private void showDateDialog(){

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                inputTgl.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd ");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

    private void writeNewUsers(String id,String nama,String jk,String tgl_lahir,String no_hp) {
        getDatabase = FirebaseDatabase.getInstance();
        getRefenence = getDatabase.getReference();
        String created_date = getCurrentDate();
        User user = new User(nama,jk,tgl_lahir,no_hp,created_date);
        getRefenence.child("User").child(id).setValue(user);
    }

    private void createUser(String email,String password){
        final String nama = inputNama.getText().toString().trim();
        final String tgl_lahir = inputTgl.getText().toString().trim();
        final String no_hp = inputHp.getText().toString().trim();
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser userData = auth.getCurrentUser();
                        writeNewUsers(userData.getUid(),nama,jenis_kelamin,tgl_lahir,no_hp);
                        sendEmailVerification();
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void sendEmailVerification() {
        final FirebaseUser userData = auth.getCurrentUser();
        userData.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this,
                                    "Verification email sent to " + userData.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignupActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.laki:
                if (checked)
                    jenis_kelamin = "Laki-laki";
                    break;
            case R.id.perempuan:
                if (checked)
                    jenis_kelamin = "Perempuan";
                    break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}