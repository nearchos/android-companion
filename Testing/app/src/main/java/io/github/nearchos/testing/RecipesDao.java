package io.github.nearchos.testing;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.github.nearchos.testing.model.Ingredient;
import io.github.nearchos.testing.model.IngredientToRecipe;
import io.github.nearchos.testing.model.Recipe;

/** Realizes the data API for the Recipes App */
@Dao
public interface RecipesDao {

    @Insert
    long insert(Recipe recipe);

    @Insert
    long insert(Ingredient ingredient);

    @Insert
    long [] insertAll(Ingredient... ingredients);

    @Insert
    void insert(IngredientToRecipe ingredientToRecipe);

    @Update
    void update(Recipe recipe); // identified by _id

    @Query("SELECT * FROM ingredients_to_recipes WHERE _id=:id")
    IngredientToRecipe get(long id);

    @Query("SELECT * FROM recipes ORDER BY creationTimestamp")
    List<Recipe> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE _id=:id")
    Recipe getRecipe(long id);

    @Query("SELECT * FROM ingredients ORDER BY name")
    List<Ingredient> getAllIngredients();

    @Query("SELECT * FROM ingredients WHERE _id IN (:recipeIds)")
    List<Ingredient> getIngredientIdsForRecipe(List<Long> recipeIds);

    @Query("SELECT * FROM ingredients_to_recipes WHERE recipe_id=:recipeId")
    List<IngredientToRecipe> getIngredientToRecipeForRecipe(long recipeId);

    @Query("SELECT ingredient_id FROM ingredients_to_recipes WHERE recipe_id=:recipeId")
    List<Long> getIngredientIdsForRecipe(long recipeId);

    @Query("SELECT quantity FROM ingredients_to_recipes WHERE recipe_id=:recipeId AND ingredient_id=:ingredientId")
    double getQuantityForIngredient(long recipeId, long ingredientId);

    @Delete
    void delete(Recipe recipe);

    @Query("DELETE FROM recipes WHERE _id=:id")
    void deleteRecipe(long id);

    @Delete
    void delete(IngredientToRecipe ingredientToRecipe);
}
