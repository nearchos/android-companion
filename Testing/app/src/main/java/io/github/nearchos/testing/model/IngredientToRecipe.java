package io.github.nearchos.testing.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "ingredients_to_recipes",
    foreignKeys = {
        @ForeignKey(entity = Recipe.class, parentColumns = "_id", childColumns = "recipe_id", onDelete = CASCADE),
        @ForeignKey(entity = Ingredient.class, parentColumns = "_id", childColumns = "ingredient_id")},
    indices ={ @Index("recipe_id"), @Index("ingredient_id")})
public class IngredientToRecipe implements Serializable {

    @PrimaryKey(autoGenerate = true) private long _id;
    @ColumnInfo(name = "recipe_id") private long recipeId;
    @ColumnInfo(name = "ingredient_id") private  long ingredientId;
    private Unit unit;
    private double quantity;

    public IngredientToRecipe(long _id, long recipeId, long ingredientId, Unit unit, double quantity) {
        this._id = _id;
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.unit = unit;
        this.quantity = quantity;
    }

    public long getId() {
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

    public double getQuantity() {
        return quantity;
    }

    @NonNull
    @Override
    public String toString() {
        return quantity + " " + unit.getFullName() + "(s) of " + ingredientId;
    }
}