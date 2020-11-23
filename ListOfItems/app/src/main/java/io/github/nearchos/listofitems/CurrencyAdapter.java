package io.github.nearchos.listofitems;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class CurrencyAdapter extends ArrayAdapter<Currency> {

    public CurrencyAdapter(@NonNull Context context, @NonNull Currency[] objects) {
        super(context, R.layout.item_currency, objects);
    }

    @NonNull @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.item_currency, parent, false);
        }

        final Currency currency = getItem(position); // access the selected item

        // access the UI elements from the layout
        CheckBox favoriteCheckBox = view.findViewById(R.id.favorite);
        TextView codeTextView = view.findViewById(R.id.code);
        TextView nameTextView = view.findViewById(R.id.name);

        // initialize the UI elements
        favoriteCheckBox.setChecked(currency.isFavorite());
        codeTextView.setText(currency.getCode());
        nameTextView.setText(currency.getName());

        // we can even set a listener to any of the UI elements
        favoriteCheckBox.setOnCheckedChangeListener((button, isChecked) ->
                Toast.makeText(getContext(), currency.getCode() + ": " + isChecked, Toast.LENGTH_SHORT).show());

        return view;
    }
}