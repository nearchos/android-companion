package io.github.nearchos.testing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import io.github.nearchos.testing.model.Recipe;

public class MainActivity extends AppCompatActivity {

    private RecipesDao recipesDao;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipesDao = RecipesDatabase.getDatabase(this).recipesDao();

        listView = findViewById(R.id.listView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final List<Recipe> recipes = recipesDao.getAllRecipes();

        final ArrayAdapter<Recipe> recipeArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, recipes);
        listView.setAdapter(recipeArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Recipe selectedRecipe = recipes.get(i);
                // start activity to edit it
                final Intent intent = new Intent(MainActivity.this, EditRecipeActivity.class);
                intent.putExtra("recipeId", selectedRecipe.getId());
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Recipe deletedRecipe = recipes.remove(i);
                recipesDao.delete(deletedRecipe);
                recipeArrayAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

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