package me.vale.springtryfirstapp.services;

import me.vale.springtryfirstapp.model.Recipe;

public interface RecipeService {

    int addRecipe(Recipe recipe);

    Recipe getRecipe(int id);
}
