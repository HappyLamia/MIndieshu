package com.orion.darmawan.eclinic;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orion.darmawan.eclinic.Model.ModelData;
import com.orion.darmawan.eclinic.Model.User;
import com.orion.darmawan.eclinic.Util.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText inputNama,inputTgl,inputHp;
    private TextView id_member,nama,email,phone,gender,birthday;
    private RadioButton jklaki,jkperempuan;
    private Button btnEdit;
    private FirebaseAuth auth;
    private FirebaseDatabase getDatabase;
    private DatabaseReference getRefenence;
    public String jenis_kelamin;
    public String cdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ModelData userProfil = SharedPrefManager.getInstance(this).getUser();
        auth = FirebaseAuth.getInstance();
        final FirebaseUser userData = auth.getCurrentUser();

        if (userProfil.getId()==null){
            setContentView(R.layout.activity_edit_profile);

            btnEdit = (Button) findViewById(R.id.update_btn);
            inputNama = (EditText) findViewById(R.id.nama_member);
            inputTgl = (EditText) findViewById(R.id.tgl_lhr);
            inputHp = (EditText) findViewById(R.id.no_hp);
            jklaki = (RadioButton) findViewById(R.id.jklaki);
            jkperempuan = (RadioButton) findViewById(R.id.jkperempuan);

            getDatabase = FirebaseDatabase.getInstance();
            getRefenence = getDatabase.getReference();
            getRefenence.child("User").child(userData.getUid()).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get user value
                            User user = dataSnapshot.getValue(User.class);
                            inputNama.setText(user.namauser);
                            inputTgl.setText(user.tgl_lahir);
                            inputHp.setText(user.no_hp);
                            if (user.jk.equals("Laki-laki")){
                                jklaki.setChecked(true);
                                jkperempuan.setChecked(false);
                            }else{
                                jklaki.setChecked(false);
                                jkperempuan.setChecked(true);
                            }
                            cdate = user.created_date;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(EditProfileActivity.this, "getUser:onCancelled"+databaseError.toException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

            inputTgl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDateDialog();
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String nama = inputNama.getText().toString();
                    String tgl_lahir = inputTgl.getText().toString();
                    String no_hp = inputHp.getText().toString();
                    UpdateUsers(userData.getUid(),nama,jenis_kelamin,tgl_lahir,no_hp,cdate);

                }
            });
        }
        else{
            setContentView(R.layout.activity_edit_profile2);

            id_member = (TextView) findViewById(R.id.id_member);
            nama = (TextView) findViewById(R.id.name);
            email = (TextView) findViewById(R.id.email);
            gender = (TextView) findViewById(R.id.gender);
            phone = (TextView) findViewById(R.id.phone);
            birthday = (TextView) findViewById(R.id.birthday);

            id_member.setText(userProfil.getId());
            nama.setText(userProfil.getName());
            gender.setText(userProfil.getGender());
            email.setText(userData.getEmail());
        }
    }

    private void UpdateUsers(String id,String nama,String jk,String tgl_lahir,String no_hp,String created_date) {
        getDatabase = FirebaseDatabase.getInstance();
        getRefenence = getDatabase.getReference();
        User user = new User(nama,jk,tgl_lahir,no_hp,created_date,"User","Level");
        getRefenence.child("User").child(id).setValue(user);
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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.jklaki:
                if (checked)
                    jenis_kelamin = "Laki-laki";
                break;
            case R.id.jkperempuan:
                if (checked)
                    jenis_kelamin = "Perempuan";
                break;
        }
    }
}
