package io.github.nearchos.testing;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

/**
 * Used to view and edit a selected recipe. The user can edit the name description, and pick a
 * preparation time from a SeekBar.
 * Finally, it enables viewing, adding or deleting ingredients to a list.
 */
public class EditRecipeActivity extends AppCompatActivity {

    // options for the preparation time SeekBar
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

    private Map<Long,Ingredient> ingredientMap = new HashMap<>();

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

        // init map with all ingredient ids to ingredients - needed for formatting ingredients list
        final List<Ingredient> allIngredients = recipesDao.getAllIngredients();
        for(final Ingredient ingredient : allIngredients) {
            ingredientMap.put(ingredient.getId(), ingredient);
        }

        // prepare the ingredients list and list adapter (empty when the activity is created)
        ingredientToRecipeArrayAdapter = new ArrayAdapter<>(
                this, R.layout.ingredient_list_item, flattenIngredientToRecipes
        );
        ingredientsListView.setAdapter(ingredientToRecipeArrayAdapter);

        // clicking on an ingredient produces a dialog showing all conversions for the ingredient's unit
        ingredientsListView.setOnItemClickListener((parent, view, position, id) -> {
            final FlattenIngredientToRecipe selectedFlattenIngredientToRecipe = flattenIngredientToRecipes.get(position);
            final Unit source  = selectedFlattenIngredientToRecipe.getUnit();
            final UnitType selectedUnitType = source .getUnitType();

            final Vector<String> conversions = new Vector<>(); // init an empty list to add all possible conversions
            for(Unit target : Unit.values()) { // iterate all units ...
                if(target.getUnitType() == selectedUnitType // ... and pick those of same type (e.g. weight, volume) ...
                        && target != source ) { // ... and exclude the unit matching the source ...
                    // check if a conversion exists from source to target ...
                    final Double equivalentQuantity = Conversions.convert(source , target, selectedFlattenIngredientToRecipe.getQuantity());
                    // ... and if so, add it to the list
                    if(!Double.isNaN(equivalentQuantity)) conversions.add(" ~ " + equivalentQuantity + " " + target.getShortName());
                }
            }

            // if no conversions were found, add a message instead
            if(conversions.isEmpty()) conversions.add(getString(R.string.No_conversions_available_for_this_unit));

            // show conversion metrics as an alert dialog
            new AlertDialog.Builder(this)
                    .setTitle(selectedFlattenIngredientToRecipe.toString())
                    .setItems(conversions.toArray(new String[0]), null)
                    .setPositiveButton(R.string.Dismiss, (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        });

        // a long click deletes the ingredient without warning (OK for a toy/testing app, but not for a commercial one)
        ingredientsListView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            // delete selected ingredient
            final FlattenIngredientToRecipe flattenIngredientToRecipe = flattenIngredientToRecipes.remove(i);
            final IngredientToRecipe ingredientToRecipe = recipesDao.get(flattenIngredientToRecipe.getIngredientToRecipeId());
            recipesDao.delete(ingredientToRecipe);
            ingredientToRecipeArrayAdapter.notifyDataSetChanged();
            return true;
        });

        // handle changes to the 'preparation time' SeekBar, e.g. by updating the label
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

    private long recipeId; // current recipe

    @Override
    protected void onResume() {
        super.onResume();

        // take recipe id from intent ...
        this.recipeId = getIntent().getLongExtra("recipeId", 0L);

        // ... and use id to load recipe from DAO
        final Recipe recipe = recipesDao.getRecipe(recipeId);
        assert recipe != null;

        // init UI elements
        nameEditText.setText(recipe.getName());
        descriptionEditText.setText(recipe.getDescription());

        // get ingredients for specified recipe and populate ingredients list
        flattenIngredientToRecipes.clear();
        final List<IngredientToRecipe> ingredientToRecipeList = recipesDao.getIngredientToRecipeForRecipe(recipeId);
        for(final IngredientToRecipe ingredientToRecipe : ingredientToRecipeList) {
            flattenIngredientToRecipes.add(new FlattenIngredientToRecipe(ingredientToRecipe, ingredientMap));
        }
        ingredientToRecipeArrayAdapter.notifyDataSetChanged();

        final int index = Arrays.binarySearch(PREPARATION_TIMES, recipe.getPreparationTimeInMinutes());
        seekBar.setProgress(index);
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
            recipesDao.deleteRecipe(recipeId);
        } else {
            // update recipe in DAO
            Recipe recipe = new Recipe(recipeId, name, description, System.currentTimeMillis(), preparationTime);
            recipesDao.update(recipe);
        }
    }

    @Override
    public void onBackPressed() {
        save();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        save();
        super.onDestroy();
    }
}