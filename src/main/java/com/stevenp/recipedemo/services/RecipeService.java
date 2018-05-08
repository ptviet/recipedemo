package com.stevenp.recipedemo.services;

import com.stevenp.recipedemo.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
