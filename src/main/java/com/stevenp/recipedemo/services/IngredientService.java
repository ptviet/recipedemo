package com.stevenp.recipedemo.services;

import com.stevenp.recipedemo.domain.Ingredient;

public interface IngredientService {
    Ingredient findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
