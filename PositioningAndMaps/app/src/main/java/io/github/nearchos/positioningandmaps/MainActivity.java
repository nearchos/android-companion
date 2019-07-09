package io.github.nearchos.positioningandmaps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import static android.location.LocationManager.*;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView coordinatesTextView;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatesTextView = findViewById(R.id.activity_main_coordinates);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onStart() { // called when the activity starts
        super.onStart();

        requestLocation(); // on activity start, request async location updates
    }

    public static final int REQUEST_CODE = 1042; // a unique code

    private void requestLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && // only check if Android 6 or higher
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // permission is not granted, so request it
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else { // permission already granted - proceed with requesting location updates
            locationManager.requestLocationUpdates(GPS_PROVIDER, 1000, 100, this);
            locationManager.requestLocationUpdates(NETWORK_PROVIDER, 1000, 100, this);
            locationManager.requestLocationUpdates(PASSIVE_PROVIDER, 1000, 100, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // granted!
                requestLocation();
            } else { // not granted :-(
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                finish(); // exit activity
            }
        }
    }

    @Override
    protected void onStop() { // called when the activity stops
        super.onStop();

        locationManager.removeUpdates(this); // stop listening to location updates
    }

    @Override
    public void onLocationChanged(Location location) { // handle location changes
        coordinatesTextView.setText(location == null ? "unknown" :
                String.format(Locale.US, "%.2f, %.2f", location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) { /* empty */ }

    @Override
    public void onProviderEnabled(String s) { /* empty */ }

    @Override
    public void onProviderDisabled(String s) { /* empty */ }

    public void showMap(View view) {
        startActivity(new Intent(this, MapsActivity.class));
    }
}