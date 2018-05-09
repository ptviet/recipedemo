package com.stevenp.recipedemo.services;

import com.stevenp.recipedemo.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
