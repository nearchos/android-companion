package io.github.nearchos.coffeelovers;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

import io.github.nearchos.coffeelovers.db.DbOpenHelper;
import io.github.nearchos.coffeelovers.model.Coffee;
import io.github.nearchos.coffeelovers.model.Order;

public class OrderViewFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public OrderViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment.
     *
     * @return A new instance of fragment OrderViewFragment.
     */
    public static OrderViewFragment newInstance() {
        return new OrderViewFragment();
    }

    private EditText orderNameEditText;
    private RadioButton cappuccinoRadioButton;
    private RadioButton latteRadioButton;
    private RadioButton americanoRadioButton;
    private Spinner sizeSpinner;
    private CheckBox caffeineFreeCheckBox;
    private Spinner milkSpinner;
    private TextView quantityTextView;
    private TextView summaryTextView;
    private Button placeOrderButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_order_view, container, false);

        this.orderNameEditText = view.findViewById(R.id.activity_add_or_edit_order_name);
        this.cappuccinoRadioButton = view.findViewById(R.id.activity_add_or_edit_order_cappuccino_radio_button);
        this.latteRadioButton = view.findViewById(R.id.activity_add_or_edit_order_latte_radio_button);
        this.americanoRadioButton = view.findViewById(R.id.activity_add_or_edit_order_americano_radio_button);
        this.sizeSpinner = view.findViewById(R.id.activity_add_or_edit_order_size_spinner);
        this.caffeineFreeCheckBox = view.findViewById(R.id.activity_add_or_edit_order_caffeine_free_checkbox);
        this.milkSpinner = view.findViewById(R.id.activity_add_or_edit_order_milk_spinner);
        this.quantityTextView = view.findViewById(R.id.activity_add_or_edit_order_quantity_label);
        this.summaryTextView = view.findViewById(R.id.activity_add_or_edit_order_summary_text_view);
        this.placeOrderButton = view.findViewById(R.id.place_order_button);

        this.sizeSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Coffee.Size.values()));
        this.sizeSpinner.setOnItemSelectedListener(this);

        this.caffeineFreeCheckBox.setOnCheckedChangeListener((compoundButton, b) -> updateSummary());

        this.milkSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Coffee.Milk.values()));
        this.milkSpinner.setOnItemSelectedListener(this);

        final Button increaseQuantityButton = view.findViewById(R.id.activity_add_or_edit_order_increase_quantity);
        increaseQuantityButton.setOnClickListener(view1 -> updateQuantity(+1));
        final Button decreaseQuantityButton = view.findViewById(R.id.activity_add_or_edit_order_decrease_quantity);
        decreaseQuantityButton.setOnClickListener(view1 -> updateQuantity(-1));

        this.placeOrderButton.setOnClickListener(view1 -> placeOrder());

        return view;
    }

    private Coffee.Type getSelectedType() {
        if(cappuccinoRadioButton.isChecked()) return Coffee.Type.CAPPUCCINO;
        else if(latteRadioButton.isChecked()) return Coffee.Type.LATTE;
        else /* assume americanoRadioBautton is checked */ return Coffee.Type.AMERICANO;
    }

    public static final int MIN_QUANTITY = 1;
    public static final int MAX_QUANTITY = 10;
    private int quantity = MIN_QUANTITY;

    private void updateQuantity(int diff) {
        quantity += diff;
        if(quantity < MIN_QUANTITY) quantity = MIN_QUANTITY;
        if(quantity > MAX_QUANTITY) quantity = MAX_QUANTITY;
        quantityTextView.setText(String.format(Locale.ENGLISH, "%d", quantity));
        updateSummary();
    }

    void update(final Order order) {
        if(order != null) {
            // set the initial values accordingly
            id = order.getId();
            orderNameEditText.setText(order.getName());
            switch (order.getCoffee().getType()) {
                case CAPPUCCINO:
                    cappuccinoRadioButton.setChecked(true);
                    break;
                case LATTE:
                    latteRadioButton.setChecked(true);
                    break;
                case AMERICANO:
                default:
                    americanoRadioButton.setChecked(true);
            }
            final Coffee.Size size = order.getCoffee().getSize();
            sizeSpinner.setSelection(size.ordinal(), true);

            caffeineFreeCheckBox.setChecked(order.getCoffee().isCaffeineFree());

            final Coffee.Milk milk = order.getCoffee().getMilk();
            milkSpinner.setSelection(milk.ordinal(), true);

            this.quantity = order.getQuantity();
            quantityTextView.setText(String.format(Locale.ENGLISH, "%d", quantity));

            updateSummary();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        updateSummary();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // nothing
    }

    private void updateSummary() {
        final Coffee.Type type = getSelectedType();
        final String sizeOption = Coffee.Size.values()[sizeSpinner.getSelectedItemPosition()].name();
        final boolean caffeineFree = caffeineFreeCheckBox.isChecked();
        final String milkOption = Coffee.Milk.values()[milkSpinner.getSelectedItemPosition()].name();
        final String summary = quantity + " of " + type.name() + ", size " + sizeOption + ", " + (caffeineFree ? "decaffeinated " : "") + "with " + milkOption;

        summaryTextView.setText(summary);
    }

    private int id = 0;

    private void placeOrder() {
        final String name = orderNameEditText.getText().toString();
        final Coffee.Type type = getSelectedType();
        final Coffee.Size size = Coffee.Size.values()[sizeSpinner.getSelectedItemPosition()];
        final boolean caffeineFree = caffeineFreeCheckBox.isChecked();
        final Coffee.Milk milk = Coffee.Milk.values()[milkSpinner.getSelectedItemPosition()];

        final Coffee coffee = new Coffee(type, size, milk, caffeineFree);
        final Order order = new Order(id, name, coffee, quantity);

        final SQLiteOpenHelper sqLiteOpenHelper = new DbOpenHelper(getActivity());
        DbOpenHelper.addOrEditOrder(sqLiteOpenHelper.getWritableDatabase(), order);

        // todo show snackbar
    }
}