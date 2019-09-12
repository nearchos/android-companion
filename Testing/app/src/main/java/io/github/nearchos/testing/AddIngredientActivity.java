package io.github.nearchos.testing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;
import java.util.Vector;

import io.github.nearchos.testing.model.Ingredient;
import io.github.nearchos.testing.model.IngredientToRecipe;
import io.github.nearchos.testing.model.Unit;

/**
 * This activity customizes an ingredient. It allows the user to pick an ingredient (like 'flour)
 * from a pre-selected list, a preferred unit type (like 'cup') and quantity.
 */
public class AddIngredientActivity extends AppCompatActivity {

    private RecipesDao recipesDao;

    private Spinner ingredientSpinner;
    private Spinner unitSpinner;
    private EditText quantityEditText;

    private List<Ingredient> ingredients;
    private List<Unit> selectedUnits = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        recipesDao = RecipesDatabase.getDatabase(this).recipesDao();

        this.ingredientSpinner = findViewById(R.id.ingredientsSpinner);
        this.unitSpinner = findViewById(R.id.unitSpinner);
        this.quantityEditText = findViewById(R.id.quantityEditText);

        // prepare ingredients spinner
        ingredients = recipesDao.getAllIngredients();
        ArrayAdapter<Ingredient> ingredientArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, ingredients);
        ingredientSpinner.setAdapter(ingredientArrayAdapter);

        // prepare units spinner
        final ArrayAdapter<Unit> unitArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, selectedUnits);
        unitSpinner.setAdapter(unitArrayAdapter);

        ingredientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // select compatible units
                final Ingredient selectedIngredient = ingredients.get(i);
                selectedUnits.clear();
                for(final Unit unit : Unit.values()) {
                    if(unit.getUnitType() == selectedIngredient.getUnitType()) {
                        selectedUnits.add(unit);
                    }
                }
                // update the units spinner
                unitArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { /* empty */ }
        });
    }

    private long recipeId;

    @Override
    protected void onResume() {
        super.onResume();

        this.recipeId = getIntent().getLongExtra("recipeId", 0L);
    }

    /**
     * When the user selects an ingredient, it is added to the database and the activity is
     * finalized, returning to the previous one.
     *
     * @param view
     */
    public void select(View view) {
        // save
        final Ingredient selectedIngredient = (Ingredient) ingredientSpinner.getSelectedItem();
        final Unit selectedUnit = (Unit) unitSpinner.getSelectedItem();
        String quantityString = quantityEditText.getText().toString();
        double quantity = 1d;
        try { quantity = Double.parseDouble(quantityString); } catch (NumberFormatException nfe) {}

        IngredientToRecipe ingredientToRecipe = new IngredientToRecipe(0L, recipeId, selectedIngredient.getId(), selectedUnit, quantity);
        recipesDao.insert(ingredientToRecipe);
        finish();
    }
}