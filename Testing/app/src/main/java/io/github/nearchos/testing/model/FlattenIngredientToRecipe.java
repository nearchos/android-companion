package io.github.nearchos.testing.model;

import java.util.Map;

/**
 * @author Nearchos
 * Created: 19-Jul-19
 */
public class FlattenIngredientToRecipe {

    private final long ingredientToRecipeId;
    private final String asString;

    public FlattenIngredientToRecipe(final IngredientToRecipe ingredientToRecipe, final Map<Long,Ingredient> ingredientMap) {
        this.ingredientToRecipeId = ingredientToRecipe.getId();
        this.asString = ingredientToRecipe.getQuantity() + " " + ingredientToRecipe.getUnit().getShortName() + " of " + ingredientMap.get(ingredientToRecipe.getIngredientId());
    }

    public long getIngredientToRecipeId() {
        return ingredientToRecipeId;
    }

    @Override
    public String toString() {
        return asString;
    }
}