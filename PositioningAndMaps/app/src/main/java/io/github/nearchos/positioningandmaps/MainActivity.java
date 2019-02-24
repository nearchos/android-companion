package io.github.nearchos.positioningandmaps;

import android.content.Intent;
import android.location.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        long minTime = 60*1000; // 60 sec min interval for reporting a new location
        float minDistance = 100; // 100 m min distance for reporting a new location
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                minTime, minDistance, this); // add 'this' as observer
    }

    @Override
    protected void onStop() { // called when the activity stops
        super.onStop();
        locationManager.removeUpdates(this); // remove 'this' from observing
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