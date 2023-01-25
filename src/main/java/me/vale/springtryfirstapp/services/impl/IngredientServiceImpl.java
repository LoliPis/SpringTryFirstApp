package me.vale.springtryfirstapp.services.impl;

import me.vale.springtryfirstapp.model.Ingredient;
import me.vale.springtryfirstapp.services.IngredientService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private int id = 0;

    private static Map<Integer, Ingredient> ingredients = new LinkedHashMap<>();

    @Override
    public int addIngredient(Ingredient ingredient) {
        ingredients.put(id, ingredient);
        return id++;
    }

    @Override
    public Ingredient getIngredient(int id) {
        return ingredients.get(id);
    }
}
