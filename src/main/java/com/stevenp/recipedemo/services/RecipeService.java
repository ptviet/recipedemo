package com.stevenp.recipedemo.services;

import com.stevenp.recipedemo.commands.RecipeCommand;
import com.stevenp.recipedemo.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeCommand findCommandById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
    void deleteById(Long idToDelete);
}
