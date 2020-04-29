package com.izrael.coronaindonesia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class Laporan extends Fragment {

    FirebaseUser      user;
    DatabaseReference reference;
    String            nama, alamat, nik;
    Button kirim, covid;
    String iduser, nohp;
    private EditText pesan, tempatkejadian;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_laporan, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        kirim = v.findViewById(R.id.laporan);

        final String userid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Test").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profil p = dataSnapshot.getValue(profil.class);
                nohp = p.getNohp();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        covid =v.findViewById(R.id.covid);
        covid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Test_covid19.class).putExtra("userid", userid).putExtra("nohp", nohp));

            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(getActivity(),LaporanActivity.class);
            startActivity(i);
            }
        });
        return v;
    }

}
