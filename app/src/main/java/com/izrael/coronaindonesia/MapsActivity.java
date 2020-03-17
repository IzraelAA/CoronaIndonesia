package com.izrael.coronaindonesia;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final int         MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445;
    public static final  int         PATTERN_DASH_LENGTH_PX                      = 10;
    public static final  int         PATTERN_GAP_LENGTH_PX                       = 10;
    public static final  PatternItem DOT                                         = new Dot();
    public static final  PatternItem DASH                                        = new Dash(PATTERN_DASH_LENGTH_PX);
    public static final  PatternItem GAP                                         = new Gap(PATTERN_GAP_LENGTH_PX);

    public static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);


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
    Double          distance1,distance2;

    LatLng latLng = new LatLng(-6.2758471,107.2544972);
    LatLng latLng1 = new LatLng(-6.3053208,106.8996445);
    LatLng latLng2 = new LatLng(-6.340507,107.1939298);
    LatLng latLng3 = new LatLng(-6.3410215,107.1801674);

    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        latlngs.add(latLng);
        latlngs.add(latLng1);
        latlngs.add(latLng2);
        latlngs.add(latLng3);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
for (int i = 0;i<latlngs.size();i++){
    mMap.addMarker(new MarkerOptions().position(latlngs.get(i)).title("Marker"));
    mMap.getUiSettings().setZoomControlsEnabled(true);
    mMap.moveCamera(CameraUpdateFactory.newLatLng(latlngs.get(i)));
    try {
        GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.es_geojson, getApplicationContext());

        GeoJsonPolygonStyle style = layer.getDefaultPolygonStyle();
        style.setFillColor(Color.MAGENTA);
        style.setStrokeColor(Color.MAGENTA);
        style.setStrokeWidth(1F);

        layer.addLayerToMap();

    } catch (IOException ex) {
        Log.e("IOException", ex.getLocalizedMessage());
    } catch (JSONException ex) {
        Log.e("JSONException", ex.getLocalizedMessage());
    }
}
        // Add a marker in Sydney and move the camera
    }

    private void marker() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
    }
}
