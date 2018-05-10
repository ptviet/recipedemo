package com.stevenp.recipedemo.services;

import com.stevenp.recipedemo.commands.IngredientCommand;
import com.stevenp.recipedemo.converters.IngredientToIngredientCommand;
import com.stevenp.recipedemo.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.stevenp.recipedemo.domain.Ingredient;
import com.stevenp.recipedemo.domain.Recipe;
import com.stevenp.recipedemo.repositories.IngredientRepository;
import com.stevenp.recipedemo.repositories.RecipeRepository;
import com.stevenp.recipedemo.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    IngredientRepository ingredientRepository;

    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {

    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(recipeRepository, unitOfMeasureRepository, ingredientRepository);
    }

    @Test
    public void findByRecipeIdAndId() throws Exception {
    }

    @Test
    public void findByRecipeIdAndRecipeId() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //then
        Ingredient ingredient = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), ingredient.getId());
        assertEquals(Long.valueOf(1L), ingredient.getRecipe().getId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testSaveRecipe() throws Exception {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        ingredient.setRecipe(new Recipe());
        ingredient.getRecipe().setId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        Ingredient savedIngredient = ingredientService.saveIngredient(ingredient);


        //then
        assertEquals(Long.valueOf(3L), savedIngredient.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testDeleteById() throws Exception {

        ingredientRepository.deleteById(2L);

        verify(ingredientRepository, times(1)).deleteById(anyLong());

    }

}
