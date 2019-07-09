package io.github.nearchos.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView headingTextView;
    private ImageView compassImageView;
    private SensorManager sensorManager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.headingTextView = findViewById(R.id.headingTextView);
        this.compassImageView = findViewById(R.id.compassImageView);

        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override protected void onStart() {
        super.onStart();
        // register for updates from the 'orientation' sensor
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override public void onSensorChanged(SensorEvent sensorEvent) {
        final float headingInDegrees = sensorEvent.values[0]; // compass in radians
        update(headingInDegrees);
    }

    @Override public void onAccuracyChanged(Sensor sensor, int i) { /* ignore */ }

    private void update(final float headingInDegrees) {
        headingTextView.setText(String.format(Locale.US, "Your phone is heading towards %.1f°", headingInDegrees));
    }
}