package com.izrael.coronaindonesia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Registrasi extends AppCompatActivity {

    private TextInputLayout layoutnama, layoutpassword, layoutnik , layoutnohp;
    String nama,password,nik,nohp,codesend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        Button Registrasi = findViewById(R.id.Register);
        layoutnama = findViewById(R.id.nama);
        layoutpassword = findViewById(R.id.Password);
        layoutnik = findViewById(R.id.Nik);
        layoutnohp = findViewById(R.id.NoHp);

        Registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = layoutnama.getEditText().getText().toString();
                password   =layoutpassword.getEditText().getText().toString();
                nik   = layoutnik.getEditText().getText().toString();
                nohp = layoutnohp.getEditText().getText().toString();
                if (nohp.isEmpty()){
                    layoutnohp.setError("Phone number is required");
                    layoutnohp.requestFocus();
                }else if (nama.isEmpty()){
                    layoutnama.setError("Nama is required");
                    layoutnama.requestFocus();
                }else if (password.isEmpty()){
                    layoutpassword.setError("Nama is required");
                    layoutpassword.requestFocus();
                }else if (nik.isEmpty()){
                    layoutnik.setError("Nik is required");
                    layoutnik.requestFocus();
                }else {
                    confrimInput(nama,nik,password,nohp);
                }
            }
        });


    }
    public void confrimInput(final String nama, String nik, String password,String nohp) {
           // OnVerificationStateChangedCallbacks

                    Intent intent = new Intent(Registrasi.this,verifcation.class);
                    intent.putExtra("nama",nama);
                    intent.putExtra("nik",nik);
                    intent.putExtra("password",password);
                    intent.putExtra("nohp",nohp);
                    startActivity(intent);


    }

}
