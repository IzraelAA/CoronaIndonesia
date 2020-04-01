package com.izrael.coronaindonesia;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import android.widget.*;
public class BlankFragment extends Fragment {

    DatabaseReference reference;
    FirebaseUser      user;
    EditText nama,nik;
    TextView nomer,warning;
    Button btnsbmt;
    ProgressBar pg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View           v      = inflater.inflate(R.layout.fragment_blank, container, false);
        nama   = v.findViewById(R.id.etname);
        user = FirebaseAuth.getInstance().getCurrentUser();
        warning = v.findViewById(R.id.warning);
        nomer = v.findViewById(R.id.nomer);
        btnsbmt = v.findViewById(R.id.change);
        pg = v.findViewById(R.id.loading);
        pg.setVisibility(View.GONE);
        btnsbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upload();
            }
        });
        String         userid = user.getUid();
        
        reference = FirebaseDatabase.getInstance().getReference("Test").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               profil p = dataSnapshot.getValue(profil.class);
               nomer.setText("Nomer :"+p.getNohp());
                Log.d("panca",""+p.getNama());
                Log.d("panca",""+p.getNohp());
               if (p.getNama().equals("")){
                   warning.setVisibility(View.VISIBLE);
               }else {
                   nama.setText(p.getNama());
                   warning.setVisibility(View.GONE);
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }

    public void upload() {
        pg.setVisibility(View.VISIBLE);
        HashMap<String, Object> hashmap = new HashMap<>();

        hashmap.put("nama", nama.getText().toString());
        reference.updateChildren(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pg.setVisibility(View.GONE);

            }
        });
    }

}
