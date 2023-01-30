package me.vale.springtryfirstapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.vale.springtryfirstapp.model.Recipe;
import me.vale.springtryfirstapp.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "CRUD-операции и другие эндпоинты для работы с рецептами")
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    @Operation(
            summary = "Добавление рецепта",
            description = "Можно добавить рецепты в соответствии со схемой объекта"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт добавлен"
            )
    })
    public ResponseEntity<Integer> addRecipe(@RequestBody Recipe recipe) {
        int id  = recipeService.addRecipe(recipe);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение рецепта",
            description = "Можно получить рецепт по номеру id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт найден"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт по введенному id не найден"
            )
    })
    public ResponseEntity<Recipe> getRecipeById(@PathVariable int id) {
        Recipe recipe =  recipeService.getRecipe(id);
        if (recipe == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/getAllRecipes")
    @Operation(
            summary = "Получение рецептов",
            description = "Можно получить все имеющиеся рецепты"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепты найдены"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ни один рецепт не найден"
            )
    })
    public ResponseEntity<Map<Integer, Recipe>> getAllRecipes(){
        if (recipeService.getAllRecipes() != null) {
            return ResponseEntity.ok().body(recipeService.getAllRecipes());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Редактирование рецепта",
            description = "Можно изменить данные о рецепте по его id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт успешно изменен"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт по введенному id не найден, либо измененные " +
                            "данные введены некорректно"
            )
    })
    public ResponseEntity<Recipe> editRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        Recipe recipe1 = recipeService.editRecipe(id, recipe);
        if (recipe1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe1);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление рецепта",
            description = "Можно удалить рецепт по id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Такой рецепт не найден"
            )
    })
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id){
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
