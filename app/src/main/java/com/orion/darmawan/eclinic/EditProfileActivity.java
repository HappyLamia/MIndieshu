package com.orion.darmawan.eclinic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orion.darmawan.eclinic.Model.User;

public class EditProfileActivity extends AppCompatActivity {

    private EditText inputNama,inputTgl,inputHp;
    private RadioButton jklaki,jkperempuan;
    private Button btnEdit;
    private FirebaseAuth auth;
    private FirebaseDatabase getDatabase;
    private DatabaseReference getRefenence;
    public String jenis_kelamin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btnEdit = (Button) findViewById(R.id.update_btn);
        inputNama = (EditText) findViewById(R.id.nama_member);
        inputTgl = (EditText) findViewById(R.id.tgl_lhr);
        inputHp = (EditText) findViewById(R.id.no_hp);
        jklaki = (RadioButton) findViewById(R.id.jklaki);
        jkperempuan = (RadioButton) findViewById(R.id.jkperempuan);

        auth = FirebaseAuth.getInstance();
        getDatabase = FirebaseDatabase.getInstance();
        getRefenence = getDatabase.getReference();

        FirebaseUser userData = auth.getCurrentUser();
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
