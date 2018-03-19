package com.hackathon.cyberblue.crimeagainstwomenapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
//import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.widget.AdapterView.*;
import static android.widget.Toast.LENGTH_LONG;
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener
        /*OnMyLocationClickListener*/{

    private GoogleMap mMap;
    private int[] lat={16,30};
    private int[] lon={80,90};

    public DatabaseReference databaseReference;



    public String state,name;
    public String description;
    public String crimeType;
    public double saveLat,saveLong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        EditText date1=(EditText)findViewById(R.id.datepicket);

        date1.setText(getCurrentTimeStamp());


        databaseReference= FirebaseDatabase.getInstance().getReference("Crime");
        //-----------------------------------------------------------------
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
       // spinner.setOnItemSelectedListener(this);

// Create an ArrayAdapter using the string array and a default spinner layout
      //  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        //        R.array.state_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        //spinner.setAdapter(adapter);
        //----------------------------------------------------------------

    //-----------------------------------------------------------------
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.crime_arrays, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);
        //----------------------------------------------------------------
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemIdAtPosition(position);
                crimeType=String.valueOf(spinner1.getSelectedItem());
                Toast.makeText(MapsActivity.this, String.valueOf(spinner1.getSelectedItem()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            parent.getItemIdAtPosition(position);
            state=String.valueOf(spinner.getSelectedItem());
            mMap.clear();
            Toast.makeText(MapsActivity.this, String.valueOf(spinner.getSelectedItem()), Toast.LENGTH_SHORT).show();
            LatLng sydney = new LatLng(lat[position], lon[position]);

           mMap.addMarker(new MarkerOptions().position(sydney).title(String.valueOf(spinner.getSelectedItem())));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });

        }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this,"hi",Toast.LENGTH_SHORT);
            return;
        }
        else

        {
            Toast.makeText(this,"bye",Toast.LENGTH_SHORT);
        }
        mMap.setMyLocationEnabled(true);
      // mMap.setOnMyLocationButtonClickListener((GoogleMap.OnMyLocationButtonClickListener) this);
       // mMap.setOnMyLocationClickListener((OnMyLocationClickListener) this);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();

                //Do what you want on obtained latLng
                Toast.makeText(MapsActivity.this,String.valueOf(latLng), Toast.LENGTH_SHORT).show();
                saveLat=(Double)latLng.latitude;
                saveLong= (Double)latLng.longitude;
                mMap.addMarker(new MarkerOptions().position(latLng).title("TITLE"));

            }
        });
    }

/* @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();

        mMap.setMaxZoomPreference(5);
    }
*/
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return true;
    }


    public void Enter(View view) {
        EditText Des=(EditText)findViewById(R.id.edit);
        EditText name=(EditText)findViewById(R.id.name);
        EditText date1=(EditText)findViewById(R.id.datepicket);
        String des=Des.getText().toString();
        String n1=name.getText().toString();
        String day1=getCurrentTimeStamp();
        Log.i("Date", day1);
        Crime c=new Crime(state,crimeType,"LOCATION",des, n1, day1);
        Crime c1=new Crime(saveLat,saveLong);
        String s=databaseReference.push().getKey();
        databaseReference.child(s).setValue(c);

        databaseReference.child(s).child("location").setValue(c1);
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();

    }
    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
