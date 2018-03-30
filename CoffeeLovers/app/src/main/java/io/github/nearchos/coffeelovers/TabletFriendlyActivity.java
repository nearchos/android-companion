package io.github.nearchos.coffeelovers;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.github.nearchos.coffeelovers.model.Order;

public class TabletFriendlyActivity extends AppCompatActivity implements OrderSelectionListener {

    private OrderViewFragment orderViewFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet_friendly);

        orderViewFragment = (OrderViewFragment) getSupportFragmentManager().findFragmentById(R.id.order_viewer_fragment);
    }

    @Override
    public void onOrderSelected(Order order) {
        if(orderViewFragment != null && orderViewFragment.isAdded()) { // dual fragment (tablet mode)
            // show in fragment
            orderViewFragment.update(order);
        } else { // single fragment (phone mode)
            final Intent intent = new Intent(this, AddOrEditOrderActivity.class);
            intent.putExtra("order", order);
            startActivity(intent);
        }
    }
}