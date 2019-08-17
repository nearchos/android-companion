package io.github.nearchos.testing;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import io.github.nearchos.testing.model.FlattenIngredientToRecipe;
import io.github.nearchos.testing.model.Ingredient;
import io.github.nearchos.testing.model.IngredientToRecipe;
import io.github.nearchos.testing.model.Recipe;
import io.github.nearchos.testing.model.Unit;
import io.github.nearchos.testing.model.UnitType;

public class EditRecipeActivity extends AppCompatActivity {

    public static final int [] PREPARATION_TIMES = {
            5, 10, 15, 20, 25, 30, 40, 45, 60, 90
    };

    private RecipesDao recipesDao;

    private EditText nameEditText;
    private EditText descriptionEditText;
    private TextView seekBarLabelTextView;
    private SeekBar seekBar;

    final List<FlattenIngredientToRecipe> flattenIngredientToRecipes = new Vector<>();
    private ArrayAdapter<FlattenIngredientToRecipe> ingredientToRecipeArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        recipesDao = RecipesDatabase.getDatabase(this).recipesDao();

        this.nameEditText = findViewById(R.id.nameEditText);
        this.descriptionEditText = findViewById(R.id.descriptionEditText);
        final ListView ingredientsListView = findViewById(R.id.ingredientsListView);
        this.seekBarLabelTextView = findViewById(R.id.seekBarLabelTextView);
        this.seekBar = findViewById(R.id.seekBar);

        ingredientToRecipeArrayAdapter = new ArrayAdapter<>(
                this, R.layout.ingredient_list_item, flattenIngredientToRecipes
        );
        ingredientsListView.setAdapter(ingredientToRecipeArrayAdapter);

        ingredientsListView.setOnItemClickListener((parent, view, position, id) -> {
            final Vector<String> conversions = new Vector<>();
            final FlattenIngredientToRecipe flattenIngredientToRecipe = flattenIngredientToRecipes.get(position);
            final Unit source  = flattenIngredientToRecipe.getUnit();
            final UnitType selectedUnitType = source .getUnitType();
            for(Unit target : Unit.values()) {
                if(target.getUnitType() == selectedUnitType && target != source ) {
                    final Double equivalentQuantity = Conversions.convert(source , target, flattenIngredientToRecipe.getQuantity());
                    if(!Double.isNaN(equivalentQuantity)) conversions.add(" ~ " + equivalentQuantity + " " + target.getShortName());
                }
            }

            if(conversions.isEmpty()) conversions.add(getString(R.string.No_conversions_available_for_this_unit));

            // show conversion metrics
            new AlertDialog.Builder(this)
                    .setTitle(flattenIngredientToRecipe.toString())
                    .setItems(conversions.toArray(new String[0]), null)
                    .setPositiveButton(R.string.Dismiss, (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        });

        ingredientsListView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            // delete selected ingredient
            final FlattenIngredientToRecipe flattenIngredientToRecipe = flattenIngredientToRecipes.remove(i);
            final IngredientToRecipe ingredientToRecipe = recipesDao.get(flattenIngredientToRecipe.getIngredientToRecipeId());
            recipesDao.delete(ingredientToRecipe);
            ingredientToRecipeArrayAdapter.notifyDataSetChanged();
            return true;
        });

        final String formattedPreparationTimeLabel = getString(R.string.Preparation_time, PREPARATION_TIMES[seekBar.getProgress()]);
        seekBarLabelTextView.setText(formattedPreparationTimeLabel);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int index, boolean b) {
                seekBarLabelTextView.setText(getString(R.string.Preparation_time, PREPARATION_TIMES[index]));
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) { /* empty */ }

            @Override public void onStopTrackingTouch(SeekBar seekBar) { /* empty */ }
        });
    }

    private long recipeId;

    @Override
    protected void onResume() {
        super.onResume();

        this.recipeId = getIntent().getLongExtra("recipeId", 0L);

        // init fields
        Recipe recipe = recipesDao.getRecipe(recipeId);
        assert recipe != null;

        nameEditText.setText(recipe.getName());
        descriptionEditText.setText(recipe.getDescription());

        final List<Ingredient> allIngredients = recipesDao.getAllIngredients();
        final Map<Long,Ingredient> ingredientMap = new HashMap<>();
        for(final Ingredient ingredient : allIngredients) {
            ingredientMap.put(ingredient.getId(), ingredient);
        }
        flattenIngredientToRecipes.clear();
        final List<IngredientToRecipe> ingredientToRecipeList = recipesDao.getIngredientToRecipeForRecipe(recipeId);
        for(final IngredientToRecipe ingredientToRecipe : ingredientToRecipeList) {
            flattenIngredientToRecipes.add(new FlattenIngredientToRecipe(ingredientToRecipe, ingredientMap));
        }
        ingredientToRecipeArrayAdapter.notifyDataSetChanged();

        final int index = Arrays.binarySearch(PREPARATION_TIMES, recipe.getPreparationTimeInMinutes());
        seekBar.setProgress(index);
    }

    @Override
    protected void onPause() {
        save();
        super.onPause();
    }

    public void addIngredient(View view) {
        Intent intent = new Intent(this, AddIngredientActivity.class);
        intent.putExtra("recipeId", recipeId);
        startActivity(intent);
    }

    public void save() {
        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        int preparationTime = PREPARATION_TIMES[seekBar.getProgress()];

        if(name.trim().isEmpty() && description.trim().isEmpty() && flattenIngredientToRecipes.isEmpty()) {
            // empty recipe, thus just delete from DAO
            recipesDao.delete(recipeId);
        } else {
            // update recipe in DAO
            Recipe recipe = new Recipe(recipeId, name, description, System.currentTimeMillis(), preparationTime);
            recipesDao.update(recipe);
        }
    }
}