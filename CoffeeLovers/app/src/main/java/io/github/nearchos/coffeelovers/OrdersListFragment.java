package io.github.nearchos.coffeelovers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.Vector;

import io.github.nearchos.coffeelovers.db.DbOpenHelper;
import io.github.nearchos.coffeelovers.model.Order;

public class OrdersListFragment extends Fragment {

    public OrdersListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment.

     * @return A new instance of fragment OrdersListFragment.
     */
    public static OrdersListFragment newInstance() {
        return new OrdersListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList(showOpenOrdersOnlyCheckBox.isChecked());
    }

    private void updateList(final boolean showOpenOrdersOnly) {
        final DbOpenHelper dbOpenHelper = new DbOpenHelper(getActivity());
        final Vector<Order> orders = DbOpenHelper.getOrders(dbOpenHelper.getReadableDatabase(), showOpenOrdersOnly);

        listView.setAdapter(new OrderAdapter(getActivity(), orders));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // todo tell the parent activity
//            final Intent intent = new Intent(this, AddOrEditOrderActivity.class);
//            intent.putExtra("order", orders.get(position));
//            startActivity(intent);
        });
    }

    private CheckBox showOpenOrdersOnlyCheckBox;
    private ListView listView;

    private boolean dualPanel = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_orders_list, container, false);
        this.showOpenOrdersOnlyCheckBox = view.findViewById(R.id.activity_main_open_orders_only_checkbox);
        this.listView = view.findViewById(R.id.list_view);
        return view;
    }
}