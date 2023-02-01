package me.vale.springtryfirstapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.vale.springtryfirstapp.model.Recipe;
import me.vale.springtryfirstapp.services.FilesService;
import me.vale.springtryfirstapp.services.RecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Value("${name.of.recipes.file}")
    private String recipeFileName;

    final private FilesService filesService;
    private static int id = 0;

    private static Map<Integer, Recipe> recipes = new LinkedHashMap<>();

    public RecipeServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @Override
    public int addRecipe(Recipe recipe) {
        recipes.put(id, recipe);
        saveToFile();
        return id++;
    }

    @Override
    public Recipe getRecipe(int id) {
       return recipes.get(id);
    }

    @Override
    public Map<Integer, Recipe> getAllRecipes(){
        return recipes;
    }

    @Override
    public Recipe editRecipe(int id, Recipe recipe){
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
            saveToFile();
            return recipes.get(id);
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(int id){
        if (recipes.containsKey(id)) {
            recipes.remove(id);
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
            String json = new ObjectMapper().writeValueAsString(recipes);
            filesService.saveToFile(json, recipeFileName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private void readFromFile() {
        String json = filesService.readFromFile(recipeFileName);
        if (json == null) return;
        try {
            recipes = new  ObjectMapper().readValue(json, new TypeReference<Map<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}