package com.example.henriquead.chatapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.henriquead.chatapp.Data.Contact;
import com.example.henriquead.chatapp.Data.Coordinates;
import com.example.henriquead.chatapp.Data.MessageDatabase;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateContactActivity extends AppCompatActivity implements OnMapReadyCallback {
    private EditText fName, lName;
    private Coordinates coordinates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        this.fName = findViewById(R.id.txtNewConctactFName);
        this.lName = findViewById(R.id.txtNewConctactLName);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, CreateContactActivity.class);
        context.startActivity(starter);
    }


    public void btnAddNewConctactToList(View view) {
        String first = this.fName.getText().toString();
        String last = this.lName.getText().toString();

        Contact contact = new Contact(0, first, last, this.coordinates);

        MessageDatabase.getInstance(this).contactDao().insert(contact);
        finish();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {




        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng));

                coordinates = new Coordinates(latLng.latitude, latLng.longitude);
            }
        });
    }
}
