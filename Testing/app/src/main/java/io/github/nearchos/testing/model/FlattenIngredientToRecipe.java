package io.github.nearchos.testing.model;

import java.util.Map;

/**
 * @author Nearchos
 * Created: 19-Jul-19
 */
public class FlattenIngredientToRecipe {

    private final long ingredientToRecipeId;
    private final String asString;

    private double quantity;
    private Unit unit;

    public FlattenIngredientToRecipe(final IngredientToRecipe ingredientToRecipe, final Map<Long,Ingredient> ingredientMap) {
        this.ingredientToRecipeId = ingredientToRecipe.getId();
        this.asString = ingredientToRecipe.getQuantity() + " " + ingredientToRecipe.getUnit().getShortName() + " of " + ingredientMap.get(ingredientToRecipe.getIngredientId());

        this.quantity = ingredientToRecipe.getQuantity();
        this.unit = ingredientToRecipe.getUnit();
    }

    public long getIngredientToRecipeId() {
        return ingredientToRecipeId;
    }

    public double getQuantity() {
        return quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return asString;
    }
}