package io.github.nearchos.coffeelovers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Vector;

import io.github.nearchos.coffeelovers.db.DbOpenHelper;
import io.github.nearchos.coffeelovers.model.Order;

/**
 * @author Nearchos
 * Created: 30-Mar-18
 */
public class OrderAdapter extends ArrayAdapter<Order> {

    private final SQLiteDatabase writableSqLiteDatabase;

    public OrderAdapter(@NonNull Context context, final Vector<Order> orders) {
        super(context, R.layout.order_list_item, orders);

        this.writableSqLiteDatabase = new DbOpenHelper(context).getWritableDatabase();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if(view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.order_list_item, null);
        }

        final Order order = getItem(position);

        final CheckBox statusCheckBox = view.findViewById(R.id.order_list_item_status);
        final TextView nameTextView = view.findViewById(R.id.order_list_item_name);
        final TextView summaryTextView = view.findViewById(R.id.order_list_item_summary);

        statusCheckBox.setChecked(order.getStatus() == Order.Status.CLOSED);
        nameTextView.setText(order.getName());
        summaryTextView.setText(order.getQuantity() + " of " + order.getCoffee().getType().name());

        statusCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                DbOpenHelper.closeOrder(writableSqLiteDatabase, order.getId());
            } else {
                DbOpenHelper.openOrder(writableSqLiteDatabase, order.getId());
            }
        });

        return view;
    }
}