package io.github.nearchos.listofitems;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class CurrenciesActivity extends AppCompatActivity {

    public static final Currency [] currencies = new Currency[] {
            new Currency("EUR", "Euro", true),
            new Currency("USD", "United States Dollar", false),
            new Currency("JPY", "Japan Yen", false),
            new Currency("GBP", "Great Britain Pound", false),
            new Currency("AUD", "Australia Dollar", false),
            new Currency("NZD", "New Zealand Dollar", false),
            new Currency("CHF", "Switzerland Franc", true),
            new Currency("RUB", "Russia Rouble", false),
            new Currency("CAD", "Canadian Dollar", false),
            new Currency("ZAR", "South African Rand", false)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencies);

        ListView currenciesListView = findViewById(R.id.currenciesListView);
        CurrencyAdapter currencyAdapter = new CurrencyAdapter(this, currencies);
        currenciesListView.setAdapter(currencyAdapter);
        currenciesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Currency selected = currencies[position];
                Toast.makeText(CurrenciesActivity.this, selected.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}