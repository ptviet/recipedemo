package com.stevenp.recipedemo.repositories;

import com.stevenp.recipedemo.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long>{

}
