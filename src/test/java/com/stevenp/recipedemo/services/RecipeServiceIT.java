package com.stevenp.recipedemo.services;

import com.stevenp.recipedemo.domain.Recipe;
import com.stevenp.recipedemo.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

    private static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Transactional
    @Test
    public void testSaveOfDescription() throws Exception {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();

        //when
        testRecipe.setDescription(NEW_DESCRIPTION);
        Recipe savedRecipe = recipeService.saveRecipe(testRecipe);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipe.getDescription());
        assertEquals(testRecipe.getId(), savedRecipe.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipe.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipe.getIngredients().size());
    }
}
