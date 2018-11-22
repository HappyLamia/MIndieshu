package com.orion.darmawan.eclinic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class EditProfileActivity extends AppCompatActivity {

    private EditText inputNama,inputTgl,inputHp;
    private TextView id_member,nama,email,phone,gender,birthday;
    private RadioButton jklaki,jkperempuan;
    private Button btnEdit;
    private FirebaseAuth auth;
    private FirebaseDatabase getDatabase;
    private DatabaseReference getRefenence;
    public String jenis_kelamin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ModelData userProfil = SharedPrefManager.getInstance(this).getUser();
        auth = FirebaseAuth.getInstance();
        FirebaseUser userData = auth.getCurrentUser();

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
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(EditProfileActivity.this, "getUser:onCancelled"+databaseError.toException(),
                                    Toast.LENGTH_SHORT).show();
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
