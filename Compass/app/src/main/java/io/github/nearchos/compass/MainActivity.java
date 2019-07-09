package io.github.nearchos.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
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

    private float currentHeadingInDegrees = 0f;

    private void update(final float headingInDegrees) {
        headingTextView.setText(String.format(Locale.US, "Your phone is heading towards %.1f°", headingInDegrees));

        // create a rotation animation
        RotateAnimation rotateAnimation = new RotateAnimation(
                currentHeadingInDegrees,
                -headingInDegrees, // reverse degrees as we actually intend to point the device and keep the image fixed
                Animation.RELATIVE_TO_SELF, 0.5f, // pivot from center horizontally...
                Animation.RELATIVE_TO_SELF, 0.5f); // ///and vertically

        rotateAnimation.setDuration(10); // the animation lasts 10ms
        rotateAnimation.setFillAfter(true); // persist at the end

        // Start the animation
        compassImageView.startAnimation(rotateAnimation);
        currentHeadingInDegrees = -headingInDegrees;
    }
}