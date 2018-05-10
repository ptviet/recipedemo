package com.stevenp.recipedemo.services;

import com.stevenp.recipedemo.commands.RecipeCommand;
import com.stevenp.recipedemo.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    Recipe saveRecipe(Recipe recipe);
    void deleteById(Long idToDelete);
}
