package me.vale.springtryfirstapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.vale.springtryfirstapp.model.Ingredient;
import me.vale.springtryfirstapp.services.FilesService;
import me.vale.springtryfirstapp.services.IngredientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Value("${name.of.ingredients.file}")
    private String ingredientFileName;

    final private FilesService filesService;

    private int id = 0;

    private static Map<Integer, Ingredient> ingredients = new LinkedHashMap<>();

    public IngredientServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @Override
    public int addIngredient(Ingredient ingredient) {
        ingredients.put(id, ingredient);
        saveToFile();
        return id++;
    }

    @Override
    public Ingredient getIngredient(int id) {
        return ingredients.get(id);
    }

    @Override
    public Map<Integer, Ingredient> getAllIngredients(){
        return ingredients;
    }

    @Override
    public Ingredient editIngredient(int id, Ingredient ingredient){
        if (ingredients.containsKey(id)) {
            ingredients.put(id, ingredient);
            saveToFile();
            return ingredients.get(id);
        }
        return null;
    }

    @Override
    public boolean deleteIngredient(int id){
        if (ingredients.containsKey(id)) {
            ingredients.remove(id);
            return true;
        }
        return false;
    }

    @PostConstruct
    private void init(){
        readFromFile();
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            filesService.saveToFile(json, ingredientFileName);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        String json = filesService.readFromFile(ingredientFileName);
        if (json == null) return;
        try {
            ingredients = new  ObjectMapper().readValue(json, new TypeReference<LinkedHashMap<Integer, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
