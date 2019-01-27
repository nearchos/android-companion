package io.github.nearchos.coffeelovers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.Vector;

import io.github.nearchos.coffeelovers.db.DbOpenHelper;
import io.github.nearchos.coffeelovers.model.Order;

public class MainActivity extends AppCompatActivity {

    private CheckBox showOpenOrdersOnlyCheckBox;
    private Button tabletFriendlyButton;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.showOpenOrdersOnlyCheckBox = findViewById(R.id.activity_main_open_orders_only_checkbox);
        this.tabletFriendlyButton = findViewById(R.id.activity_main_tablet_friendly_button);
        this.listView = findViewById(R.id.list_view);

        this.showOpenOrdersOnlyCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateList(isChecked));
        this.tabletFriendlyButton.setOnClickListener(v -> startActivity(new Intent(this, TabletFriendlyActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateList(showOpenOrdersOnlyCheckBox.isChecked());
    }

    private void updateList(final boolean showOpenOrdersOnly) {
        final DbOpenHelper dbOpenHelper = new DbOpenHelper(this);
        final Vector<Order> orders = DbOpenHelper.getOrders(dbOpenHelper.getReadableDatabase(), showOpenOrdersOnly);

        listView.setAdapter(new OrderAdapter(this, orders));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            final Intent intent = new Intent(this, AddOrEditOrderActivity.class);
            intent.putExtra("order", orders.get(position));
            startActivity(intent);
        });
    }

    public void addOrder(final View view) {
        startActivity(new Intent(this, AddOrEditOrderActivity.class));
    }
}