package io.github.nearchos.testing;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import io.github.nearchos.testing.model.Recipe;

/**
 * The main activity of the app.
 * It shows a list of recipes and offers an option to a add a new recipe.
 * By selecting a recipe you view it in edit mode.
 * A long press deletes a recipe.
 */
public class MainActivity extends AppCompatActivity {

    private RecipesDao recipesDao;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipesDao = RecipesDatabase.getDatabase(this).recipesDao();

        listView = findViewById(R.id.recipesListView);
    }

    /**
     * Initializes the list with data from DAO.
     */
    @Override
    protected void onResume() {
        super.onResume();

        final List<Recipe> recipes = recipesDao.getAllRecipes();

        final ArrayAdapter<Recipe> recipeArrayAdapter = new ArrayAdapter<>(
                this, R.layout.recipe_list_item, recipes);
        listView.setAdapter(recipeArrayAdapter);

        // a plain click opens the recipe for viewing/editing
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Recipe selectedRecipe = recipes.get(i);
            // start activity to edit it
            final Intent intent = new Intent(MainActivity.this, EditRecipeActivity.class);
            intent.putExtra("recipeId", selectedRecipe.getId());
            startActivity(intent);
        });

        // a long click deletes the recipe without warning (OK for a toy/testing app, but not for a commercial one)
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Recipe deletedRecipe = recipes.remove(i);
            recipesDao.delete(deletedRecipe);
            recipeArrayAdapter.notifyDataSetChanged();
            return true;
        });
    }

    /**
     * Creates and stores a new {@link Recipe} and passes it on to the {@link EditRecipeActivity}.
     * The empty {@link Recipe} is needed so that the {@link EditRecipeActivity} can add
     * {@link io.github.nearchos.testing.model.Ingredient}s to it. If that activity is finalized
     * before adding any {@link io.github.nearchos.testing.model.Ingredient}s and before setting a
     * title or description, then the {@link Recipe} is deleted.
     *
     * @param view required to enable direct invocation from the layout element
     */
    public void addRecipe(View view) {
        // create and store a new, empty recipe
        Recipe recipe = new Recipe(0L, "", "", System.currentTimeMillis(), EditRecipeActivity.PREPARATION_TIMES[3]);
        final long recipeId = recipesDao.insert(recipe);

        // start activity to edit it
        final Intent intent = new Intent(MainActivity.this, EditRecipeActivity.class);
        intent.putExtra("recipeId", recipeId);
        startActivity(intent);
    }
}