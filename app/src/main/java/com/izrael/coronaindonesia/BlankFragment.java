package com.izrael.coronaindonesia;

import android.content.Context;
import android.content.Intent;
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
    TextView          nomer, warning, nama, nik, alamat;
    Button btnedit, logout;
    ProgressBar pg;
    String      namateks, nikteks, alamatteks,nomerteks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        nama = v.findViewById(R.id.etname);
        nik = v.findViewById(R.id.nik);
        alamat = v.findViewById(R.id.alamat);
        user = FirebaseAuth.getInstance().getCurrentUser();
        warning = v.findViewById(R.id.warning);
        nomer = v.findViewById(R.id.nomer);
        logout = v.findViewById(R.id.logout);
        btnedit = v.findViewById(R.id.edit);
        pg = v.findViewById(R.id.loading);
        pg.setVisibility(View.GONE);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),HomeActivity.class));
            }
        });
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditActivty.class);
                intent.putExtra("nama", namateks);
                intent.putExtra("alamat", alamatteks);
                intent.putExtra("nik", nikteks);
                intent.putExtra("nomer", nomerteks);
                startActivity(intent);
            }
        });
        String userid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Test").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profil p = dataSnapshot.getValue(profil.class);
                nomerteks = p.getNohp();
                nomer.setText("Nomer  : " + nomerteks);
                Log.d("panca", "" + p.getNama());
                Log.d("panca", "" + p.getNohp());
                if (p.getNama() == null) {
                    warning.setText("Silakan Isi Nama");
                    warning.setVisibility(View.VISIBLE);
                } else {

                    namateks =  p.getNama();
                    nama.setText("Nama   : " +namateks);
                    warning.setVisibility(View.GONE);
                }
                if (p.getAlamat() == null) {
                    warning.setText("Silakan Isi Alamat");
                    warning.setVisibility(View.VISIBLE);
                } else {
                    alamatteks =  p.getAlamat();
                    alamat.setText("Alamat : " +alamatteks);
                    warning.setVisibility(View.GONE);
                }
                if (p.getNik() == null) {
                    warning.setText("Silakan isi nik");
                    warning.setVisibility(View.VISIBLE);
                } else {
                    nikteks =  p.getNik();
                    nik.setText("Nik        : " +nikteks);
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

    @Override
    public void onStart() {
        super.onStart();
    }
}
