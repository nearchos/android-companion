package io.github.nearchos.coffeelovers;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TabletFriendlyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet_friendly);
    }

    public void addOrder(final View view) {
        startActivity(new Intent(this, AddOrEditOrderActivity.class));
    }
}