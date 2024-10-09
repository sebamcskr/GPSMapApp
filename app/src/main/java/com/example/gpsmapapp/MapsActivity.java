package com.example.gpsmapapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.gpsmapapp.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private final int LOCATION_REQUEST_CODE = 101;
    private Marker currentLocationMarker;
    /*⠀⠀⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⡈⠀⢀⣴⣦⢶⣴⣴⣦⢶⣴⢦⣶⡴⣦⡶⣴⣦⢶⣴⣤⡀⠀
            ⠀⠀⣿⣟⣾⠗⢀⢸⣾⢿⣽⣻⣾⠻⠝⠛⢉⡈⠛⠻⢾⣿⠀⠀⠀
            ⠀⠀⣿⣾⠷⠞⣹⡿⣯⢿⡞⠋⣡⣴⣾⢿⣟⡿⣟⣷⣄⠹⠀⠀⠀
            ⠀⠀⣿⣯⣤⣼⡿⣽⡛⢁⣴⣿⣻⣽⡾⣯⡿⣽⣟⣷⣻⡆⠀⠀⠀
            ⠀⠀⣿⣽⣻⢯⡟⠃⣴⣿⣻⢾⣽⣳⡿⣽⣻⣽⡾⣯⣷⢿⠀⠀⠀
            ⠀⠀⣿⣳⡿⠏⣠⣿⣟⣷⣻⣯⣟⣷⣿⣻⣽⣷⣻⢷⣯⣿⠀⠀⠀
            ⠀⠀⣿⣽⠃⣴⣿⣳⢿⣞⡿⣿⠆⠀⠀⢠⣿⣞⣯⣿⣳⡏⠀⠀⠀
            ⠀⠀⣿⠃⣼⣻⢾⣽⣯⣿⠟⠁⠀⣠⣄⣸⣿⢾⣽⣞⣯⠅⠀⠀⠀
            ⠀⠀⡇⣸⣷⣻⣯⣷⣻⣿⡀⣠⠞⠹⣿⣿⢯⣿⣞⣯⠏⢸⠀⠀⠀
            ⠀⠀⢁⣿⣽⣯⢿⣽⡟⢿⠗⠉⠀⢠⣿⣽⢾⣯⣟⡿⢀⣾⠀⠀⠀
            ⠀⠀⢸⣟⣾⡽⣯⣿⠀⠀⠀⣠⣶⣿⣻⢾⣟⣾⡽⢀⣾⣻⠀⠀⠀
            ⠀⠀⣿⣟⡷⣿⣻⣿⣤⣤⣤⣬⣿⣷⣻⣯⢿⠞⢠⣾⢯⣿⠀⠀⠀
            ⠀⠀⣿⣽⣻⢷⣟⣿⣻⣟⣿⣻⣟⡾⣷⡻⠋⣰⣿⢯⣿⣽⠀⠀⠀
            ⠀⠀⢻⣷⣻⢿⣾⣳⡿⣾⡽⣷⣻⡽⠛⣠⣾⢿⡽⠿⢾⣽⠀⠀⠀
            ⠀⠐⡌⢳⣿⣻⡾⣽⣻⢷⣟⠏⢃⣤⣾⢿⡽⡟⣁⣤⣾⣿⠀⠀⠀
            ⠀⠀⣿⢦⣈⣑⠛⠛⣋⣁⣤⡾⣿⡽⣾⣻⡏⠛⣡⣾⣟⣾⠀⠀⠀
            ⠃⠀⠙⢿⣯⣟⣿⣻⣯⣿⣽⣻⣷⣟⣯⣷⣷⣶⣿⣳⡯⠋⠀⠀⠀
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            return;
        }


        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        getCurrentLocation();
    }


    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                    if (currentLocationMarker != null) {
                        currentLocationMarker.remove();
                    }
                    currentLocationMarker = mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Estás aquí"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    getCurrentLocation();
                }
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}