package com.izrael.coronaindonesia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.util.Log;

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

public class verifcation extends AppCompatActivity {

    private TextInputLayout code;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    DatabaseReference reference;
    String            nama,password,nik,nohp,codesend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifcation);
        code = findViewById(R.id.code);
        progressBar = findViewById(R.id.progressbar);
        final Button verif  = findViewById(R.id.Register);
        TextView nonya = findViewById(R.id.textnohp);
        Bundle       bundle = getIntent().getExtras();
        nohp = bundle.getString("nohp");
        nonya.setText(nohp);
        mAuth = FirebaseAuth.getInstance();
        Log.d("pp:","pp : "+nohp);
        verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                verfiysigin();
            }
        });
        cekverif();
    }

    private void cekverif() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                nohp,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
    }

    public void verfiysigin(){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesend, code.getEditText().getText().toString());
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("aaa", "signInWithCredential:success");

                            FirebaseUser user   = task.getResult().getUser();
                            final String userid = user.getUid();
                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String, String> hashmap = new HashMap<>();
                            hashmap.put("nohp", nohp);
                            reference.setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(verifcation.this,Test_covid.class).putExtra("userid",userid).putExtra("nohp",nohp));
                                        finish();
                                    }
                                }
                            });
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("aaa", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            signInWithPhoneAuthCredential(phoneAuthCredential);
            Toast.makeText(verifcation.this,"Nomer udah terdaftar",Toast.LENGTH_LONG);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(verifcation.this,"Gagal",Toast.LENGTH_LONG);
            Log.d("aaa", "signInWithCredential:" +e);

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            String b =s;
            Log.d("haha", "onCodeSent: "+b);
            codesend = b;
        }
    };
}
