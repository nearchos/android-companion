package io.github.nearchos.archcomp.power;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import io.github.nearchos.archcomp.MainActivity;
import io.github.nearchos.archcomp.R;

import static android.content.Intent.ACTION_BATTERY_CHANGED;
import static android.content.Intent.ACTION_POWER_CONNECTED;
import static android.content.Intent.ACTION_POWER_DISCONNECTED;

public class PowerActivity extends AppCompatActivity {

    private ImageView imageViewPowerConnected;
    private TextView textViewPowerConnected;
    private TextView textViewBatteryLevel;

    private PowerReceiver powerReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);

        imageViewPowerConnected = findViewById(R.id.imageViewPowerConnected);
        textViewPowerConnected = findViewById(R.id.textViewPowerConnected);
        textViewBatteryLevel = findViewById(R.id.textViewBatteryLevel);

        final PowerViewModel powerViewModel = new ViewModelProvider(this).get(PowerViewModel.class);
        powerViewModel.getPowerInfo().observe(this, powerInfo -> {
            imageViewPowerConnected.setImageResource(powerInfo.isPowerConnected() ? R.drawable.ic_plugged : R.drawable.ic_unplugged);
            textViewPowerConnected.setText(powerInfo.isPowerConnected() ? R.string.Power_connected : R.string.Power_disconnected);
            textViewBatteryLevel.setText(getString(R.string.Battery_level, powerInfo.getBatteryLevel()));
        });
        powerReceiver = new PowerReceiver(powerViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_POWER_CONNECTED);
        intentFilter.addAction(ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(ACTION_BATTERY_CHANGED);
        registerReceiver(powerReceiver, intentFilter);
        Log.d(MainActivity.TAG, "Registered receiver!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(powerReceiver);
        Log.d(MainActivity.TAG, "Unregistered receiver!");
    }
}