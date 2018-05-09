package com.stevenp.recipedemo.services;

import com.stevenp.recipedemo.commands.IngredientCommand;
import com.stevenp.recipedemo.converters.IngredientToIngredientCommand;
import com.stevenp.recipedemo.domain.Recipe;
import com.stevenp.recipedemo.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()) {
            log.error("Recipe Not Found");
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<IngredientCommand> ingredientCommandOptional
                    = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                    .findFirst();

            if(!ingredientCommandOptional.isPresent()){
                log.error("Ingredient Not Found");
            } else {
                return ingredientCommandOptional.get();
            }
        }
        return null;
    }

}
