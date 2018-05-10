package com.stevenp.recipedemo.controllers;


import com.stevenp.recipedemo.domain.Recipe;
import com.stevenp.recipedemo.services.IngredientService;
import com.stevenp.recipedemo.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping({"/recipe/{recipeId}/ingredient"})
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting ingredients for recipe id: " + recipeId);

        Recipe recipe = recipeService.findById(Long.valueOf(recipeId));

        // use command object to avoid lazy load errors in Thymeleaf
        model.addAttribute("recipe", recipe);

        log.debug("Recipe returned: " + recipe.getDescription());

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping({"/recipe/{recipeId}/ingredient/{ingredientId}"})
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId,
                                       Model model) {
        log.debug("Getting ingredient id: " + ingredientId);
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));

        return "recipe/ingredient/show";
    }
}
