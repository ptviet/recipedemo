package com.stevenp.recipedemo.services;

import com.stevenp.recipedemo.domain.Ingredient;
import com.stevenp.recipedemo.domain.Recipe;
import com.stevenp.recipedemo.repositories.IngredientRepository;
import com.stevenp.recipedemo.repositories.RecipeRepository;
import com.stevenp.recipedemo.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientRepository = ingredientRepository;
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

    @Override
    @Transactional
    public Ingredient saveIngredient(Ingredient ingredient) {

        log.debug("saveIngredient() Ingredient: " + ingredient.getDescription());

        log.debug("Recipe: " + ingredient.getRecipe());

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredient.getRecipe().getId());
        log.debug("recipeOptional: " + recipeOptional.get().getDescription());
        if(!recipeOptional.isPresent()) {
            log.error("Recipe Not Found");
            return new Ingredient();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional
                    = recipe.getIngredients().stream()
                    .filter(ingre -> ingre.getId().equals(ingredient.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient foundIngredient = ingredientOptional.get();
                foundIngredient.setDescription(ingredient.getDescription());
                foundIngredient.setAmount(ingredient.getAmount());
                foundIngredient.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredient.getUnitOfMeasure().getId())
                                                .orElseThrow(() -> new RuntimeException("UoM Not Found")));

            } else {
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            return  savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredient.getId()))
                    .findFirst().get();
        }
    }

    @Override
    @Transactional
    public void deleteById(Long idToDelete) {
        log.debug("Deleting Id: " + idToDelete);
        ingredientRepository.deleteById(idToDelete);

    }
}
