package com.stevenp.recipedemo.controllers;

import com.stevenp.recipedemo.entity.Category;
import com.stevenp.recipedemo.entity.UnitOfMeasure;
import com.stevenp.recipedemo.repositories.CategoryRepository;
import com.stevenp.recipedemo.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"","/","/index","/home"})
    public String getIndexPage() {

//        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
//        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
//
//        System.out.println("Cat Id: " + categoryOptional.get().getId());
//        System.out.println("UoM Id: " + unitOfMeasureOptional.get().getId());

        return "index";
    }

}
