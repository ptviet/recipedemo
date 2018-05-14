package com.stevenp.recipedemo.controllers;

import com.stevenp.recipedemo.domain.Recipe;
import com.stevenp.recipedemo.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {

        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}")
    public String showById(@PathVariable String id, Model model){

        model.addAttribute("recipe", recipeService.findById(new Long(id)));
        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "recipe/recipeForm";

    }

    @GetMapping("/recipe/{id}/edit")
    public String editRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(new Long(id)));
        return "recipe/recipeForm";

    }

    @PostMapping("recipe")
    public String saveRecipe(@ModelAttribute Recipe recipe) {
        Recipe savedRecipe = recipeService.saveRecipe(recipe);

        return "redirect:" + savedRecipe.getId();
    }


    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {

        recipeService.deleteById(new Long(id));
        return "redirect:/";
    }




}