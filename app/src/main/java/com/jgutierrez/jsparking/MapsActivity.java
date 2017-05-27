package com.jgutierrez.jsparking;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker Marcador;
    double latitud = 0.0;
    double longitud = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        miUbicacion();

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setIndoorLevelPickerEnabled(true);

        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Refparqueaderos = database.getReference("Parqueadero");

        Refparqueaderos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot location : dataSnapshot.getChildren()) {

                    LatLng Parqueaderos = new LatLng(location.child("latitud").getValue(Float.class), location.child("longitud").getValue(Float.class));
                    mMap.addMarker(new MarkerOptions().position(Parqueaderos).title(location.child("Marker").getValue(String.class))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2)));
                    // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Parqueaderos, 16));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void MostrarMarcador(double latitud, double longitud) {

        LatLng MisCoordenadas = new LatLng(latitud, longitud);

        CameraUpdate EstoyAqui = CameraUpdateFactory.newLatLngZoom(MisCoordenadas, 16);
        if (Marcador != null) Marcador.remove();
        Marcador = mMap.addMarker(new MarkerOptions()
                .position(MisCoordenadas).title("Estoy Aqui")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2)));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(MisCoordenadas));
        mMap.animateCamera(EstoyAqui);

    }
    private void actualizarPosicion(Location Location) {

        if (Location != null) {
            latitud = Location.getLatitude();
            longitud = Location.getLongitude();
            MostrarMarcador(latitud, longitud);
        }
    }
    LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            actualizarPosicion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }


    };
    private void miUbicacion() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location Location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        actualizarPosicion(Location);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 15000, 0, locationListener);
    }
    }
