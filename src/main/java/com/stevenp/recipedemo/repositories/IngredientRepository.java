package com.stevenp.recipedemo.repositories;

import com.stevenp.recipedemo.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
