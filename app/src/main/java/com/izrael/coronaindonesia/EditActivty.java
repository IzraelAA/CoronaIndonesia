package com.izrael.coronaindonesia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditActivty extends AppCompatActivity {
    EditText nama, alamat, nik;
    ProgressBar pg;
    String            s   ;
    String            a  ;
    String            z;
    FirebaseUser      user;
    DatabaseReference reference;
    Button            save;
    Toolbar           toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activty);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profil");
        Intent intent = getIntent();
        String nomor = intent.getStringExtra("nomer");
         s      = intent.getStringExtra("nama");
         a      = intent.getStringExtra("alamat");
         z      = intent.getStringExtra("nik");
        getSupportActionBar().setSubtitle(nomor);
        nama = findViewById(R.id.nama);
        pg = findViewById(R.id.progressbar);
        user = FirebaseAuth.getInstance().getCurrentUser();
        alamat = findViewById(R.id.alamat);
        nik = findViewById(R.id.nik);
        save = findViewById(R.id.save);
        nama.setText(s);
        alamat.setText(a);
        nik.setText(z);
        Log.d("wwe","s"+s);
        Log.d("wwe","nomer"+nomor);
        Log.d("wwe","a"+a);
        Log.d("wwe","wwe"+z);
        String userid = user.getUid();
        pg.setVisibility(View.GONE);
        reference = FirebaseDatabase.getInstance().getReference("Test").child(userid);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s      = nama.getText().toString();
                a      = alamat.getText().toString();
                z      = nik.getText().toString();
                pg.setVisibility(View.VISIBLE);
                HashMap<String, Object> hashmap = new HashMap<>();
                hashmap.put("nama", s);
                hashmap.put("alamat", a);
                hashmap.put("nik", z);
                reference.updateChildren(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EditActivty.this, "Sukses", Toast.LENGTH_SHORT).show();
                        nama.setText(s);
                        alamat.setText(a);
                        nik.setText(z);
                        startActivity(new Intent(EditActivty.this,MenuActivity.class));
                        pg.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
