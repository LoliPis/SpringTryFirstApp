package me.vale.springtryfirstapp.controllers;

import me.vale.springtryfirstapp.model.Ingredient;
import me.vale.springtryfirstapp.model.Recipe;
import me.vale.springtryfirstapp.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<Integer> addRecipe(@RequestBody Recipe recipe) {
        int id  = recipeService.addRecipe(recipe);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable int id) {
        Recipe recipe =  recipeService.getRecipe(id);
        if (recipe == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/getAllIngredients")
    public ResponseEntity<Map<Integer, Recipe>> getAllRecipes(){
        if (recipeService.getAllRecipes() != null) {
            return ResponseEntity.ok().body(recipeService.getAllRecipes());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> editRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        Recipe recipe1 = recipeService.editRecipe(id, recipe);
        if (recipe1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id){
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
