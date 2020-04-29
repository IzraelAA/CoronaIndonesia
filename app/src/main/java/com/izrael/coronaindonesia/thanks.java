package com.izrael.coronaindonesia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;

public class thanks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);

        Bundle bundle = getIntent().getExtras();
        String hh = bundle.getString("inn");
        TextView text11 = findViewById(R.id.text123);
        text11.setText(hh);
        Button b = findViewById(R.id.buttonlanjutkan);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(thanks.this,MenuActivity.class));
            }
        });
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(thanks.this,MenuActivity.class));
//            }
//        },2000);
    }
}
