package me.vale.springtryfirstapp.services.impl;

import me.vale.springtryfirstapp.model.Recipe;
import me.vale.springtryfirstapp.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static int id = 0;

    private static Map<Integer, Recipe> recipes = new LinkedHashMap<>();

    @Override
    public int addRecipe(Recipe recipe) {
        recipes.put(id, recipe);
        return id++;
    }

    @Override
    public Recipe getRecipe(int id) {
       return recipes.get(id);
    }
}
