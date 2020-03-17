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

    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private ArrayList<LatLng> latlngPositif = new ArrayList<>();
    private ArrayList<LatLng> latlngSembuh = new ArrayList<>();
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
        latlngSembuh.add(latLng4);
        latlngSembuh.add(latLng5);        latlngSembuh.add(latLng6);
        latlngSembuh.add(latLng7);
        latlngPositif.add(latLng8);
        latlngPositif.add(latLng9);
        latlngPositif.add(latLng10);
        latlngPositif.add(latLng11);
        latlngPositif.add(latLng12);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (int i = 0;i<latlngs.size();i++){
            mMap.addMarker(new MarkerOptions().position(latlngs.get(i)).title("Terindekasi").icon(BitmapDescriptorFactory.fromResource(R.drawable.merah1)));
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlngs.get(i)));
        }
        for (int i = 0;i<latlngPositif.size();i++){
            mMap.addMarker(new MarkerOptions().position(latlngPositif.get(i)).title("Terjangkit").icon(BitmapDescriptorFactory.fromResource(R.drawable.kuning1)));
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlngPositif.get(i)));
        }
        for (int i = 0;i<latlngSembuh.size();i++){
            mMap.addMarker(new MarkerOptions().position(latlngSembuh.get(i)).title("Sembuh").icon(BitmapDescriptorFactory.fromResource(R.drawable.biru1)));
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlngSembuh.get(i)));
        }
        // Add a marker in Sydney and move the camera
    }
}
