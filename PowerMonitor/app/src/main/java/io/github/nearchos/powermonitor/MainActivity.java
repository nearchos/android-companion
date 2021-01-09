/*
 * Copyright (c) 2020. Nearchos Paspallis
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 United
 * States License, described at this URL: https://creativecommons.org/licenses/by-nc-nd/3.0/us/.
 * Unless required by applicable law or agreed to in writing, code listed in this site is
 * distributed on "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 */

package io.github.nearchos.powermonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Intent.ACTION_BATTERY_CHANGED;
import static android.content.Intent.ACTION_POWER_CONNECTED;
import static android.content.Intent.ACTION_POWER_DISCONNECTED;

public class MainActivity extends AppCompatActivity {

    private ImageView imageViewPowerConnected;
    private TextView textViewPowerConnected;
    private TextView textViewBatteryLevel;

    private PowerReceiver powerReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(powerReceiver);
    }
}