package me.vale.springtryfirstapp.services;

import me.vale.springtryfirstapp.model.Recipe;

import java.util.Map;

public interface RecipeService {

    int addRecipe(Recipe recipe);

    Recipe getRecipe(int id);


    Map<Integer, Recipe> getAllRecipes();

    Recipe editRecipe(int id, Recipe recipe);


    boolean deleteRecipe(int id);
}
