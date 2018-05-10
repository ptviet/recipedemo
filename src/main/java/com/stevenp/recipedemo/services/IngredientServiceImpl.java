package com.stevenp.recipedemo.services;

import com.stevenp.recipedemo.domain.Ingredient;
import com.stevenp.recipedemo.domain.Recipe;
import com.stevenp.recipedemo.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public Ingredient findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()) {
            log.error("Recipe Not Found");
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredient
                    = recipe.getIngredients().stream()
                    .filter(ingre -> ingre.getId().equals(ingredientId))
                    .findFirst();

            if(!ingredient.isPresent()){
                log.error("Ingredient Not Found");
            } else {
                return ingredient.get();
            }
        }
        return null;
    }

}
