package com.stevenp.recipedemo.controllers;

import com.stevenp.recipedemo.domain.Ingredient;
import com.stevenp.recipedemo.domain.Recipe;
import com.stevenp.recipedemo.domain.UnitOfMeasure;
import com.stevenp.recipedemo.services.IngredientService;
import com.stevenp.recipedemo.services.RecipeService;
import com.stevenp.recipedemo.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController ingredientController;

    MockMvc mockMvc;

    @Before
    public void setUp()throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

    }

    @Test
    public void testListIngredients() throws Exception {
        //given
        Recipe recipe = new Recipe();
        when(recipeService.findById(anyLong())).thenReturn(recipe);

        //when
        mockMvc.perform(get("/recipe/1/ingredient"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/ingredient/list"))
               .andExpect(model().attributeExists("recipe"));

        //then
        verify(recipeService, times(1)).findById(anyLong());

    }

    @Test
    public void testShowIngredient() throws Exception {
        //given
        Ingredient ingredient = new Ingredient();

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredient);

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));

    }

    @Test
    public void testGetNewIngredientForm() throws Exception {

        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient"));
    }


    @Test
    public void testUpdateIngredientForm() throws Exception {
        //given
        Ingredient ingredient = new Ingredient();

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredient);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void testSaveIngredient() throws Exception {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        ingredient.setRecipe(new Recipe());
        ingredient.getRecipe().setId(2L);
        ingredient.setUnitOfMeasure(new UnitOfMeasure());
        ingredient.getUnitOfMeasure().setId(1L);

        //when
        when(ingredientService.saveIngredient(any())).thenReturn(ingredient);

        //then
        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient"));

    }

    @Test
    public void testDeleteIngredient() throws Exception {

        mockMvc.perform(get("/recipe/1/ingredient/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredient"));

        verify(ingredientService, times(1)).deleteById(anyLong());
    }



}
