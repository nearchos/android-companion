package io.github.nearchos.coffeelovers;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

import io.github.nearchos.coffeelovers.db.DbOpenHelper;
import io.github.nearchos.coffeelovers.model.Coffee;
import io.github.nearchos.coffeelovers.model.Order;

public class AddOrEditOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_order);

        this.orderNameEditText = findViewById(R.id.activity_add_or_edit_order_name);
        this.cappuccinoRadioButton = findViewById(R.id.activity_add_or_edit_order_cappuccino_radio_button);
        this.latteRadioButton = findViewById(R.id.activity_add_or_edit_order_latte_radio_button);
        this.americanoRadioButton = findViewById(R.id.activity_add_or_edit_order_americano_radio_button);
        this.sizeSpinner = findViewById(R.id.activity_add_or_edit_order_size_spinner);
        this.caffeineFreeCheckBox = findViewById(R.id.activity_add_or_edit_order_caffeine_free_checkbox);
        this.milkSpinner = findViewById(R.id.activity_add_or_edit_order_milk_spinner);
        this.quantityTextView = findViewById(R.id.activity_add_or_edit_order_quantity_label);
        this.summaryTextView = findViewById(R.id.activity_add_or_edit_order_summary_text_view);
        this.placeOrderButton = findViewById(R.id.place_order_button);

        this.sizeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Coffee.Size.values()));
        this.sizeSpinner.setOnItemSelectedListener(this);

        this.caffeineFreeCheckBox.setOnCheckedChangeListener((compoundButton, b) -> updateSummary());

        this.milkSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Coffee.Milk.values()));
        this.milkSpinner.setOnItemSelectedListener(this);

        final Button increaseQuantityButton = findViewById(R.id.activity_add_or_edit_order_increase_quantity);
        increaseQuantityButton.setOnClickListener(view -> updateQuantity(+1));
        final Button decreaseQuantityButton = findViewById(R.id.activity_add_or_edit_order_decrease_quantity);
        decreaseQuantityButton.setOnClickListener(view -> updateQuantity(-1));

        this.placeOrderButton.setOnClickListener(view -> placeOrder());

        // keep the keyboard hidden
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    // 0 means new entry, otherwise edit existing
    private int id = 0;

    @Override
    protected void onResume() {
        super.onResume();

        final Intent intent = getIntent();
        final Order order = intent == null ? null : (Order) intent.getSerializableExtra("order");
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

    private void placeOrder() {
        final String name = orderNameEditText.getText().toString();
        final Coffee.Type type = getSelectedType();
        final Coffee.Size size = Coffee.Size.values()[sizeSpinner.getSelectedItemPosition()];
        final boolean caffeineFree = caffeineFreeCheckBox.isChecked();
        final Coffee.Milk milk = Coffee.Milk.values()[milkSpinner.getSelectedItemPosition()];

        final Coffee coffee = new Coffee(type, size, milk, caffeineFree);
        final Order order = new Order(id, name, coffee, quantity);

        final SQLiteOpenHelper sqLiteOpenHelper = new DbOpenHelper(this);
        DbOpenHelper.addOrEditOrder(sqLiteOpenHelper.getWritableDatabase(), order);
        finish();
    }
}