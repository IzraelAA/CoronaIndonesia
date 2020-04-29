package com.izrael.coronaindonesia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MenuActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    private final static int ID_HOME         = 1;
    private final static int ID_EXPLORE      = 2;
    private final static int ID_MESSAGE      = 3;
    private final static int ID_NOTIFICATION = 4;
    private final static int ID_ACCOUNT      = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_home_black_24dp));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_EXPLORE, R.drawable.ic_report_black_24dp));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_MESSAGE, R.drawable.ic_account_circle_black_24dp));
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                String        name;
                BlankFragment akun  = new BlankFragment();
                MapsFragment  home  = new MapsFragment();
                Laporan       lapor = new Laporan();
                switch (item.getId()) {
                    case ID_HOME:
                        name = "HOME";
                        loadFragment(home, name);
                        break;
                    case ID_EXPLORE:
                        name = "EXPLORE";
                        loadFragment(lapor, name);
                        break;
                    case ID_MESSAGE:
                        name = "MESSAGE";
                        loadFragment(akun, name);
                        break;
                }

            }
        });
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                String        name;
                BlankFragment akun  = new BlankFragment();
                MapsFragment  home  = new MapsFragment();
                Laporan       lapor = new Laporan();
                switch (item.getId()) {
                    case ID_HOME:
                        name = "HOME";
                        loadFragment(home, name);
                        break;
                    case ID_EXPLORE:
                        name = "EXPLORE";
                        loadFragment(lapor, name);
                        break;
                    case ID_MESSAGE:
                        name = "MESSAGE";
                        loadFragment(akun, name);
                        break;
                }
            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                String        name;
                BlankFragment akun  = new BlankFragment();
                MapsFragment  home  = new MapsFragment();
                Laporan       lapor = new Laporan();
                switch (item.getId()) {
                    case ID_HOME:
                        name = "HOME";
                        loadFragment(home, name);
                        break;
                    case ID_EXPLORE:
                        name = "EXPLORE";
                        loadFragment(lapor, name);
                        break;
                    case ID_MESSAGE:
                        name = "MESSAGE";
                        loadFragment(akun, name);
                        break;
                }
            }
        });
        bottomNavigation.show(ID_HOME, true);
    }

    public void loadFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout, fragment, tag)
                .commit();
    }
}