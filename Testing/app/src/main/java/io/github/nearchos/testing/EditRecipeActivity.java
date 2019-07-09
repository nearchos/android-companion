package io.github.nearchos.testing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import io.github.nearchos.testing.model.IngredientToRecipe;
import io.github.nearchos.testing.model.Recipe;

public class EditRecipeActivity extends AppCompatActivity {

    public static final int [] PREPARATION_TIMES = {
            5, 10, 15, 20, 25, 30, 40, 45, 60, 90
    };

    private RecipesDao recipesDao;

    private EditText nameEditText;
    private EditText descriptionEditText;
    private ListView ingredientsListView;
    private TextView seekBarLabelTextView;
    private SeekBar seekBar;

    final List<IngredientToRecipe> ingredientToRecipes = new Vector<>();
    private ArrayAdapter<IngredientToRecipe> ingredientToRecipeArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        recipesDao = RecipesDatabase.getDatabase(this).recipesDao();

        this.nameEditText = findViewById(R.id.nameEditText);
        this.descriptionEditText = findViewById(R.id.descriptionEditText);
        this.ingredientsListView = findViewById(R.id.ingredientsListView);
        this.seekBarLabelTextView = findViewById(R.id.seekBarLabelTextView);
        this.seekBar = findViewById(R.id.seekBar);

        ingredientToRecipeArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, ingredientToRecipes
        );
        ingredientsListView.setAdapter(ingredientToRecipeArrayAdapter);

        ingredientsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // delete selected ingredient
                final IngredientToRecipe removedIngredientToRecipe = ingredientToRecipes.remove(i);
                recipesDao.delete(removedIngredientToRecipe);
                ingredientToRecipeArrayAdapter.notifyDataSetChanged();
                return true;
            }
        });

        seekBarLabelTextView.setText(PREPARATION_TIMES[seekBar.getProgress()] + " minutes");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarLabelTextView.setText(PREPARATION_TIMES[i] + " minutes");
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

        ingredientToRecipes.addAll(recipesDao.getIngredientToRecipeForRecipe(recipeId));
        ingredientToRecipeArrayAdapter.notifyDataSetChanged();

        final int index = Arrays.binarySearch(PREPARATION_TIMES, recipe.getPreparationTimeInMinutes());
        seekBar.setProgress(index);
    }

    public void addIngredient(View view) {
        Intent intent = new Intent(this, AddIngredientActivity.class);
        intent.putExtra("recipeId", recipeId);
        startActivity(intent);
    }

    public void save(View view) {
        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        int preparationTime = PREPARATION_TIMES[seekBar.getProgress()];

        Recipe recipe = new Recipe(recipeId, name, description, System.currentTimeMillis(), preparationTime);
        recipesDao.update(recipe);

        finish();
    }
}