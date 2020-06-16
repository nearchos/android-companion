package io.github.nearchos.archcomp.power;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import io.github.nearchos.archcomp.MainActivity;

public class PowerReceiver extends BroadcastReceiver {

    private final PowerViewModel powerViewModel;

    public PowerReceiver(final PowerViewModel powerViewModel) {
        this.powerViewModel = powerViewModel;
    }

    @Override
    public void onReceive(Context context, Intent batteryStatusIntent) {

        Log.d(MainActivity.TAG, "battery -> " + batteryStatusIntent); // todo delete

        // Are we charging / charged?
        int status = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isPowerConnected = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int level = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        int batteryLevel = (int) (level * 100 / (float) scale);

        powerViewModel.setPowerInfo(isPowerConnected, batteryLevel);
    }
}