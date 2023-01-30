package me.vale.springtryfirstapp.controllers;

import me.vale.springtryfirstapp.model.Ingredient;
import me.vale.springtryfirstapp.services.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<Integer> addIngredient(@RequestBody Ingredient ingredient) {
        int id  = ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable int id) {
        Ingredient ingredient =  ingredientService.getIngredient(id);
        if (ingredient == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @GetMapping("/getAllIngredients")
    public ResponseEntity<Map<Integer, Ingredient>> getAllIngredients(){
        if (ingredientService.getAllIngredients() != null) {
            return ResponseEntity.ok().body(ingredientService.getAllIngredients());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> editIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        Ingredient ingredient1 = ingredientService.editIngredient(id, ingredient);
        if (ingredient1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id){
        if (ingredientService.deleteIngredient(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
