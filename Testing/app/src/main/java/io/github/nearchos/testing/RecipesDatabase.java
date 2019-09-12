package io.github.nearchos.testing;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

import io.github.nearchos.testing.model.Category;
import io.github.nearchos.testing.model.Ingredient;
import io.github.nearchos.testing.model.IngredientToRecipe;
import io.github.nearchos.testing.model.Recipe;
import io.github.nearchos.testing.model.UnitType;

@Database(entities = {Recipe.class, Ingredient.class, IngredientToRecipe.class}, version = 1, exportSchema = false)
@TypeConverters(DatabaseConverters.class)
public abstract class RecipesDatabase extends RoomDatabase {

    public abstract RecipesDao recipesDao();

    private static volatile RecipesDatabase INSTANCE;

    public static RecipesDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (RecipesDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RecipesDatabase.class, "recipes.db")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    // prepare database by populating default entries
                                    Executors.newSingleThreadScheduledExecutor().execute(() -> getDatabase(context).recipesDao().insertAll(INITIAL_INGREDIENTS));
                                }
                            })
                            .allowMainThreadQueries() // ok for a toy app to run DB operations on UI thread
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static final Ingredient [] INITIAL_INGREDIENTS = new Ingredient[] {
            new Ingredient(0L, "Water", Category.WATER, UnitType.VOLUME),
            new Ingredient(0L, "Milk", Category.DAIRY, UnitType.VOLUME),
            new Ingredient(0L, "Butter", Category.DAIRY, UnitType.VOLUME),
            new Ingredient(0L, "Grated cheese", Category.DAIRY, UnitType.WEIGHT),
            new Ingredient(0L, "Shredded cheese", Category.DAIRY, UnitType.VOLUME),
            new Ingredient(0L, "Egg", Category.EGGS, UnitType.ENUMERABLE),
            new Ingredient(0L, "Pork", Category.MEAT, UnitType.WEIGHT),
            new Ingredient(0L, "Olive oil", Category.OILS, UnitType.VOLUME),
            new Ingredient(0L, "Tomato", Category.VEGETABLE, UnitType.WEIGHT),
            new Ingredient(0L, "Flour", Category.GRAINS, UnitType.VOLUME),
            new Ingredient(0L, "Rice", Category.GRAINS, UnitType.VOLUME),
            new Ingredient(0L, "Pasta", Category.GRAINS, UnitType.WEIGHT),
            new Ingredient(0L, "Bell pepper", Category.VEGETABLE, UnitType.WEIGHT),
            new Ingredient(0L, "Salt", Category.SPICE, UnitType.VOLUME),
            new Ingredient(0L, "Black pepper", Category.SPICE, UnitType.VOLUME)
    };
}