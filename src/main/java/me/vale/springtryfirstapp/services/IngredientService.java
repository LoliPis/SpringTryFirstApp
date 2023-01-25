package me.vale.springtryfirstapp.services;

import me.vale.springtryfirstapp.model.Ingredient;

public interface IngredientService {

    int addIngredient(Ingredient ingredient);

    Ingredient getIngredient(int id);

}
