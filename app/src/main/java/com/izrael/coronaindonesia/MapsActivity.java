package com.izrael.coronaindonesia;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.*;
        import androidx.annotation.DrawableRes;
        import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
        import androidx.fragment.app.FragmentActivity;

        import android.Manifest;
        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.drawable.Drawable;
        import android.location.Location;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.BitmapDescriptor;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dash;
        import com.google.android.gms.maps.model.Dot;
        import com.google.android.gms.maps.model.Gap;
        import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.google.android.gms.maps.model.PatternItem;
        import com.google.android.gms.maps.model.Polyline;
        import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
        import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final int         MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445;
    public static final  int         PATTERN_DASH_LENGTH_PX                      = 10;
    public static final  int         PATTERN_GAP_LENGTH_PX                       = 10;
    public static final  PatternItem DOT                                         = new Dot();
    public static final  PatternItem DASH                                        = new Dash(PATTERN_DASH_LENGTH_PX);
    public static final  PatternItem GAP                                         = new Gap(PATTERN_GAP_LENGTH_PX);
    ScrollView mScrollView;
    public static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);


    DatabaseReference reference;
    private Marker                      currentLocationMarker;
    private Location                    currentLocation;
    private boolean                     firstTimeFlag                               = true;
    private double                      latitude, longitude;
    private TextView setLokasi;
    Polyline        polyline   = null;
    PolylineOptions polylineOptions;
    List<LatLng>    latLngList = new ArrayList<>();
    LatLng          setsatu    = null;
    List<Marker>    markerList = new ArrayList<>();
    Button          finishset;
    private FusedLocationProviderClient fusedLocationProviderClient;
    LatLng latLng = new LatLng(-6.2758471,107.2544972);
    LatLng latLng1 = new LatLng(-6.3053208,106.8996445);
    LatLng latLng2 = new LatLng(-6.533905, 107.448048);
    LatLng latLng3 = new LatLng(-6.3410215,107.1801674);
    LatLng latLng4 = new LatLng(-6.956922, 107.584691);
    LatLng latLng5 = new LatLng(-6.927209, 107.614859);
    LatLng latLng6 = new LatLng(-6.961360, 107.561492);
    LatLng latLng7 = new LatLng(-6.597611, 106.805470);
    LatLng latLng8 = new LatLng(-6.340507,107.1939298);
    LatLng latLng9 = new LatLng(-6.598582, 106.801119);
    LatLng latLng10 = new LatLng(-6.382692, 106.769069);
    LatLng latLng11 = new LatLng(-6.381226, 106.760376);
    LatLng latLng12 = new LatLng(-6.404720, 106.796021);
    FirebaseUser user;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private ArrayList<LatLng> latlngPositif = new ArrayList<>();
    private ArrayList<LatLng> latlngSembuh = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        CardView btnxit = findViewById(R.id.btnexit);
        btnxit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MapsActivity.this,HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name     = user.getDisplayName();
            // Check if user's email is verified
            String emailVerified = user.getPhoneNumber();
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
        if (mMap == null) {
            SupportMapFragment mapFragment = (WorkaroundMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap)
                {
                    mMap = googleMap;
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mMap.getUiSettings().setZoomControlsEnabled(true);

                    try {
                        // Customise the styling of the base map using a JSON object defined
                        // in a raw resource file.
                        boolean success = googleMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                        getApplicationContext(), R.raw.style_json));

                        if (!success) {
                            Log.e("", "Style parsing failed.");
                        }
                    } catch (Resources.NotFoundException e) {
                        Log.e("", "Can't find style. Error: ", e);
                    }
                    for (int i = 0;i<latlngs.size();i++){
                        mMap.addMarker(new MarkerOptions().position(latlngs.get(i)).title("Terindekasi").icon(bitmapDescriptorFromVector1(getApplicationContext(),R.drawable.ic_place_blue_24dp)));
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlngs.get(i)));
                    }
                    for (int i = 0;i<latlngPositif.size();i++){
                        mMap.addMarker(new MarkerOptions().position(latlngPositif.get(i)).title("Terjangkit").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_location_on_red_24dp)));
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlngPositif.get(i)));
                    }
                    for (int i = 0;i<latlngSembuh.size();i++){
                        mMap.addMarker(new MarkerOptions().position(latlngSembuh.get(i)).title("Sembuh").icon(bitmapDescriptorFromVector2(getApplicationContext(),R.drawable.ic_place_black_24dp)));
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlngSembuh.get(i)));
                    }

                    mMap.animateCamera(CameraUpdateFactory.zoomIn());
                    // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);
                    // Add a marker in Sydney and move the camera
                     mScrollView = findViewById(R.id.scrollMap); //parent scrollview in xml, give your scrollview id value
                    ((WorkaroundMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map))
                            .setListener(new WorkaroundMapFragment.OnTouchListener() {
                                @Override
                                public void onTouch()
                                {
                                    mScrollView.requestDisallowInterceptTouchEvent(true);
                                }
                            });
                }
            });
        }
        latlngs.add(latLng);
        latlngs.add(latLng1);
        latlngs.add(latLng2);
        latlngs.add(latLng3);
        latlngSembuh.add(latLng4);
        latlngSembuh.add(latLng5);        latlngSembuh.add(latLng6);
        latlngSembuh.add(latLng7);
        latlngPositif.add(latLng8);
        latlngPositif.add(latLng9);
        latlngPositif.add(latLng10);
        latlngPositif.add(latLng11);
        latlngPositif.add(latLng12);
        final LocationManager locationM = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if ( !locationM.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Harap Nyalakan GPS Terlebih Dahulu")
                    .setTitle("Tidak Dapat Menemukan Lokasi")
                    .setCancelable(false)
                    .setPositiveButton("Settings",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent openLocationSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    MapsActivity.this.startActivity(openLocationSettings);
                                }
                            }
                    )
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startCurrentLocationUpdates();
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_location_on_red_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    private BitmapDescriptor bitmapDescriptorFromVector1(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_place_blue_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    private BitmapDescriptor bitmapDescriptorFromVector2(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_place_black_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult.getLastLocation() == null)
                return;
            currentLocation = locationResult.getLastLocation();
            if (firstTimeFlag && mMap != null) {
                animateCamera(currentLocation);
                firstTimeFlag = false;
            }
            showMarker(currentLocation);
            upload(currentLocation);
        }
    };

    private void showMarker(@NonNull Location currentLocation) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        Log.d("ceklogtitude", "showMarker: "+currentLocation.getLatitude() + "&" +currentLocation.getLongitude());
        if (currentLocationMarker == null)
            Log.d("", "showMarker: "+currentLocationMarker);
//            currentLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location)).position(latLng));
//            currentLocationMarker = googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_location)).anchor(0.5f, 0.5f));
        else
            MarkerAnimation.animateMarkerToGB(currentLocationMarker, latLng, new LatLngInterpolator.Spherical());
    }

    private void animateCamera(@NonNull Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fusedLocationProviderClient != null)
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int                   status                = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status)
            return true;
        else {
            if (googleApiAvailability.isUserResolvableError(status))
                Toast.makeText(this, "Please Install google play services to use this application", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void startCurrentLocationUpdates() {
        //Untuk permission Lokasi GPS
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return;
            }
        }
        //Untuk data internet
        String              status        = null;
        ConnectivityManager cm            = (ConnectivityManager) MapsActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo         activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Kamu Tidak Memiliki Koneksi Internet, Harap Nyalakan Data Seluler atau Wi-Fi")
                    .setTitle("Tidak Ada Koneksi")
                    .setCancelable(false)
                    .setPositiveButton("Settings",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                                }
                            }
                    )
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startCurrentLocationUpdates();
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
        }

        //untuk cek apakah gps on/off
        final LocationManager locationM = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if ( !locationM.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Harap Nyalakan GPS Terlebih Dahulu")
                    .setTitle("Tidak Dapat Menemukan Lokasi")
                    .setCancelable(false)
                    .setPositiveButton("Settings",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent openLocationSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    MapsActivity.this.startActivity(openLocationSettings);
                                }
                            }
                    )
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startCurrentLocationUpdates();
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }
    public void upload(@NonNull Location currentLocation) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        String userid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Test").child(userid);
        HashMap<String , Object> hashmap = new HashMap<>();

        hashmap.put("location",latLng.toString());
        reference.updateChildren(hashmap);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (isGooglePlayServicesAvailable()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            startCurrentLocationUpdates();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fusedLocationProviderClient = null;
        mMap = null;
    }
    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(6).build();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                Toast.makeText(this, "Permission denied by uses", Toast.LENGTH_SHORT).show();
            else if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startCurrentLocationUpdates();
        }
    }

}

