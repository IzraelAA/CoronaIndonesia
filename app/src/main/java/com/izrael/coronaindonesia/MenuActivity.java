package com.izrael.coronaindonesia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import androidx.fragment.app.Fragment;

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
        bottomNavigation.show(ID_HOME, true);
        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {

            String name;
            @Override
            public Unit invoke(MeowBottomNavigation.Model p1) {
                int           i    = p1.getId();
                BlankFragment akun = new BlankFragment();
                MapsFragment  home = new MapsFragment();
                switch (i) {
                    case ID_HOME:
                        name = "HOME";
                        loadFragment(home,name);
                        break;
                    case ID_EXPLORE:
                        name = "EXPLORE";
                        loadFragment(akun,name);
                        break;
                    case ID_MESSAGE:
                        name = "MESSAGE";
                        loadFragment(akun,name);
                        break;
                    default:
                        name = "HOME";
                        loadFragment(home,name);
                }
                return Unit.INSTANCE;
            }
        });
        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {

            String name;
            @Override
            public Unit invoke(MeowBottomNavigation.Model p1) {
                int           i    = p1.getId();
                BlankFragment akun = new BlankFragment();
                MapsFragment  home = new MapsFragment();
                switch (i) {
                    case ID_HOME:
                        name = "HOME";
                        loadFragment(home,name);
                        break;
                    case ID_EXPLORE:
                        name = "EXPLORE";
                        loadFragment(akun,name);
                        break;
                    case ID_MESSAGE:
                        name = "MESSAGE";
                        loadFragment(akun,name);
                        break;
                    default:
                        name = "HOME";
                        loadFragment(home,name);
                }
                return Unit.INSTANCE;
            }
        });

    }
    public void loadFragment(Fragment fragment,String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout, fragment, tag)
                .addToBackStack(null)
                .commit();
    }
}