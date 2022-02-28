package io.github.nearchos.testing;

import androidx.room.Room;
import android.content.Context;

import androidx.test.filters.MediumTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.github.nearchos.testing.model.Category;
import io.github.nearchos.testing.model.Ingredient;
import io.github.nearchos.testing.model.IngredientToRecipe;
import io.github.nearchos.testing.model.Recipe;
import io.github.nearchos.testing.model.Unit;
import io.github.nearchos.testing.model.UnitType;

import static org.junit.Assert.*;

/**
 * Instrumented test, which executes on an Android device.
 *
 * It demonstrates an integration test by testing Room/DAO's API.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@MediumTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class RoomExampleInstrumentedTest {

    private static final double DELTA = 0.001d; // set the required precision to 1/1000th

    private RecipesDao recipesDao;
    private RecipesDatabase recipesDatabase;

    private long recipeId;
    private long eggId;
    private long oliveOilId;

    @Before
    public void init() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        recipesDatabase = Room.inMemoryDatabaseBuilder(context, RecipesDatabase.class).build();
        recipesDao = recipesDatabase.recipesDao();

        final Recipe recipe = new Recipe(0,
                "Fried eggs",
                "Get the oil in the pan. Make sure the oil is hot before you pour in the egg. Cook to taste.",
                System.currentTimeMillis(),
                5);
        recipeId = recipesDao.insert(recipe);

        final Ingredient egg = new Ingredient( "Eggs", Category.EGGS, UnitType.ENUMERABLE);
        final Ingredient oliveOil = new Ingredient( "Olive Oil", Category.OILS, UnitType.VOLUME);
        eggId = recipesDao.insert(egg);
        System.out.println("eggId: " + eggId);
        oliveOilId = recipesDao.insert(oliveOil);
        System.out.println("oliveOil: " + oliveOil);

        final IngredientToRecipe eggIngredients = new IngredientToRecipe(0, recipeId, eggId, Unit.EACH, 1);
        final IngredientToRecipe oliveOilIngredients = new IngredientToRecipe(0, recipeId, oliveOilId, Unit.TB, 2);
        recipesDao.insert(eggIngredients);
        recipesDao.insert(oliveOilIngredients);
    }

    @After
    public void close() {

        recipesDatabase.close();
    }

    @Test
    public void checkRecipe() {
        final List<Recipe> allRecipes = recipesDao.getAllRecipes();
        assertNotNull(allRecipes);
        assertTrue(allRecipes.size() > 0);

        final Recipe retrievedRecipe = allRecipes.get(0);
        assertEquals(retrievedRecipe.getName(), "Fried eggs");
        assertEquals(retrievedRecipe.getPreparationTimeInMinutes(), 5);
    }

    @Test
    public void checkIngredients() {
        final List<Long> ingredientIds = recipesDao.getIngredientIdsForRecipe(recipeId);
        assertNotNull(ingredientIds);

        final List<Ingredient> ingredients = recipesDao.getIngredientIdsForRecipe(ingredientIds);
        assertNotNull(ingredients);
        assertEquals(ingredients.size(), 2);
    }

    @Test
    public void checkQuantities() {
        final double eggsQuantity = recipesDao.getQuantityForIngredient(recipeId, eggId);
        assertEquals(eggsQuantity, 1, DELTA);

        final double oliveOilQuantity = recipesDao.getQuantityForIngredient(recipeId, oliveOilId);
        assertEquals(oliveOilQuantity, 2, DELTA);
    }

    @Test
    public void checkDelete() { // verify than 'deletes' cascade over to FKs
        final Recipe recipe = recipesDao.getRecipe(recipeId);
        assertNotNull(recipe);

        recipesDao.delete(recipe); // i.e. when a recipe is deleted ...

        // ... so are the corresponding entries in the ingredients_to_recipes table
        final List<Long> ingredientIds = recipesDao.getIngredientIdsForRecipe(recipeId);
        assertNotNull(ingredientIds);
        assertEquals(ingredientIds.size(), 0);
    }
}