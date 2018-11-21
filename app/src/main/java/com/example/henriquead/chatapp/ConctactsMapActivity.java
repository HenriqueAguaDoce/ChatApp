package com.example.henriquead.chatapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.henriquead.chatapp.Data.Contact;
import com.example.henriquead.chatapp.Data.MessageDatabase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class ConctactsMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static void start(Context context) {
        Intent starter = new Intent(context, ConctactsMapActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conctacts_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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


        List<Contact> contacts = MessageDatabase.getInstance(this).contactDao().getAllContacts();

        for (Contact contact : contacts){
            LatLng latLng = new LatLng(contact.getCoordinates().getLatitude(), contact.getCoordinates().getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(contact.getFullName()));
            marker.setTag(contact);
        }

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").snippet("Hello"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng));
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Contact c = (Contact) marker.getTag();



                Snackbar.make(findViewById(android.R.id.content), c.getFullName(), Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
