package com.izrael.coronaindonesia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class Test_covid19 extends AppCompatActivity {
    String userid, nohp;
    Button            submit;
    ProgressBar       progressBar;
    DatabaseReference reference;
    RadioButton       radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, radioButton7, radioButton8;
    String r1, r2, r3, r4, r5, r6, r7, r8;
    RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5, radioGroup6, radioGroup7, radioGroup8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_covid);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        radioGroup1 = findViewById(R.id.radio1);
        radioGroup2 = findViewById(R.id.radio2);
        radioGroup3 = findViewById(R.id.radio3);
        radioGroup4 = findViewById(R.id.radio4);
        radioGroup5 = findViewById(R.id.radio5);
        radioGroup6 = findViewById(R.id.radio6);
        radioGroup7 = findViewById(R.id.radio7);
        radioGroup8 = findViewById(R.id.radio8);
        submit = findViewById(R.id.Register);
        Bundle bundle = getIntent().getExtras();
        userid = bundle.getString("userid");
        nohp = bundle.getString("nohp");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                int radioId1 = radioGroup1.getCheckedRadioButtonId();
                int radioId2 = radioGroup2.getCheckedRadioButtonId();
                int radioId3 = radioGroup3.getCheckedRadioButtonId();
                int radioId4 = radioGroup4.getCheckedRadioButtonId();
                int radioId5 = radioGroup5.getCheckedRadioButtonId();
                int radioId6 = radioGroup6.getCheckedRadioButtonId();
                int radioId7 = radioGroup7.getCheckedRadioButtonId();
                int radioId8 = radioGroup8.getCheckedRadioButtonId();
                radioButton1 = findViewById(radioId1);
                radioButton2 = findViewById(radioId2);
                radioButton3 = findViewById(radioId3);
                String Status = null;
                String inn    = null;
                radioButton4 = findViewById(radioId4);
                radioButton5 = findViewById(radioId5);
                radioButton6 = findViewById(radioId6);
                radioButton7 = findViewById(radioId7);
                radioButton8 = findViewById(radioId8);
                r1 = "" + radioButton1.getText();
                r2 = "" + radioButton2.getText();
                r3 = "" + radioButton3.getText();
                r4 = "" + radioButton4.getText();
                r5 = "" + radioButton5.getText();
                r6 = "" + radioButton6.getText();
                r7 = "" + radioButton7.getText();
                r8 = "" + radioButton8.getText();
                if (r1 == null || r2 == null || r3 == null || r4 == null || r5 == null || r6 == null || r7 == null || r8 == null) {
                    Toast.makeText(Test_covid19.this, "Jangan kosongkan test tersebut", Toast.LENGTH_SHORT).show();
                } else {
                    if (r1.equals("Iya") && r2.equals("Iya") && r3.equals("Iya") && r4.equals("Iya") && r5.equals("Iya") && r6.equals("Iya") && r7.equals("Iya") && r8.equals("Iya")) {
                        Status = "Positive";
                        inn = "Berdasarkan hasil test, status kamu saat ini positif.Tetap dirumah dan isolasi diri kamu secara mandiri ya. Bila kondisi semakin memburuk, segera hubungi IGD terdekat untuk mendapatkan perawatan intensif.";
                    } else {
                        if (r1.equals("Iya") && r2.equals("Iya") && r3.equals("Iya") && r4.equals("Iya")) {
                            Status = "PDP";
                            inn = "Berdasarkan hasil test, status kamu saat ini PDP (Pasien Dalam Pengawasan). Segera periksakan dirimu ke pelayanan kesehatan terdekat ya. Tetap dirumah dan isolasi diri kamu secara mandiri.";
                        } else if (r5.equals("Iya") && r6.equals("Iya") && r7.equals("Iya") && r8.equals("Iya")) {
                            Status = "ODP";
                            inn = "Berdasarkan hasil test, status kamu saat ini ODP (Orang Dalam Pengawasan). Tetap jaga kesehatan serta jaga jarak saat beraktifitas ya.";
                        } else {
                            Status = "Negative";
                            inn = "Berdasarkan hasil test, status kamu saat ini negatif. Tetap jaga kesehatan serta jaga jarak saat beraktifitas ya.";
                        }
                    }
                }
                reference = FirebaseDatabase.getInstance().getReference("Test").child(userid).push();
                HashMap<String, String> hashmap = new HashMap<>();
                hashmap.put("userid", userid);
                hashmap.put("nohp", nohp);
                hashmap.put("1", r1);
                hashmap.put("2", r2);
                hashmap.put("3", r3);
                hashmap.put("4", r4);
                hashmap.put("5", r5);
                hashmap.put("6", r6);
                hashmap.put("7", r7);
                hashmap.put("8", r8);
                hashmap.put("status", Status);
                final String finalInn = inn;
                reference.setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            submit.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(Test_covid19.this, thanks.class);
                            intent.putExtra("inn", finalInn);
                            startActivity(intent);
                            finish();
                        } else {
                            submit.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Test_covid19.this, "gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
