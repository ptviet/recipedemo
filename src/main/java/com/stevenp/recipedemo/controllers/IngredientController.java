package com.stevenp.recipedemo.controllers;


import com.stevenp.recipedemo.domain.Ingredient;
import com.stevenp.recipedemo.domain.Recipe;
import com.stevenp.recipedemo.services.IngredientService;
import com.stevenp.recipedemo.services.RecipeService;
import com.stevenp.recipedemo.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
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

    @GetMapping
    @RequestMapping({"recipe/{recipeId}/ingredient/{ingredientId}/edit"})
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),
                                                                                        Long.valueOf(ingredientId)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveIngredient(@ModelAttribute Ingredient ingredient) {

        Ingredient savedIngredient = ingredientService.saveIngredient(ingredient);

        log.debug("Saved Recipe ID: " + savedIngredient.getRecipe().getId());
        log.debug("Saved Ingredient ID: " + savedIngredient.getId());
        log.debug("Saved Ingredient UoM ID: " + savedIngredient.getUnitOfMeasure().getId());

        return "redirect:/recipe/" + savedIngredient.getRecipe().getId() + "/ingredient";
    }

    @GetMapping
    @RequestMapping(value = "/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {

        ingredientService.deleteById(new Long(ingredientId));
        return "redirect:/recipe/" + recipeId + "/ingredient";
    }

}
