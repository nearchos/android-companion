package io.github.nearchos.testing.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * @author Nearchos
 * Created: 24-Apr-19
 */
@Entity(tableName = "ingredients_to_recipes",
    foreignKeys = {
        @ForeignKey(entity = Recipe.class, parentColumns = "_id", childColumns = "recipe_id", onDelete = CASCADE),
        @ForeignKey(entity = Ingredient.class, parentColumns = "_id", childColumns = "ingredient_id")},
    indices ={ @Index("recipe_id"), @Index("ingredient_id")})
public class IngredientToRecipe {

    @PrimaryKey(autoGenerate = true) private long _id;
    @ColumnInfo(name = "recipe_id") private long recipeId;
    @ColumnInfo(name = "ingredient_id") private  long ingredientId;
    private Unit unit;
    private int quantity;

    public IngredientToRecipe(long _id, long recipeId, long ingredientId, Unit unit, int quantity) {
        this._id = _id;
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.unit = unit;
        this.quantity = quantity;
    }

    public long get_id() {
        return _id;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public long getIngredientId() {
        return ingredientId;
    }

    public Unit getUnit() {
        return unit;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return quantity + " " + unit.getFullName() + "(s) of " + ingredientId;
    }
}