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

    private TextInputLayout layoutnama, layoutpassword, layoutnik, layoutnohp;
    String nama, password, nik, nohp, codesend;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        Button Registrasi = findViewById(R.id.Register);
        layoutnohp = findViewById(R.id.NoHp);
        progressBar = findViewById(R.id.progressbar);

        Registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nohp = layoutnohp.getEditText().getText().toString();
                if (nohp.isEmpty()) {
                    layoutnohp.setError("Phone number is required");
                    layoutnohp.requestFocus();
                } else {
                    confrimInput(nohp);
                }
            }
        });
    }

    public void confrimInput(String nohp) {
        // OnVerificationStateChangedCallbacks

        Intent intent = new Intent(Registrasi.this, verifcation.class);
        intent.putExtra("nohp", nohp);
        startActivity(intent);


    }

}
