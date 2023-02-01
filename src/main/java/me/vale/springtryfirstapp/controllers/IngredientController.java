package me.vale.springtryfirstapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.vale.springtryfirstapp.model.Ingredient;
import me.vale.springtryfirstapp.services.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "CRUD-операции и другие эндпоинты для работы с ингредиентами")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    @Operation(
            summary = "Добавление ингредиента",
            description = "Можно добавить ингредиенты в соответствии со схемой объекта"
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Ингредиент добавлен"
            )
    })
    public ResponseEntity<Integer> addIngredient(@RequestBody Ingredient ingredient) {
        int id  = ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение ингредиента",
            description = "Можно получить ингредиент по номеру id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент найден"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент по введенному id не найден"
            )
    })
    public ResponseEntity<Ingredient> getIngredient(@PathVariable int id) {
        Ingredient ingredient =  ingredientService.getIngredient(id);
        if (ingredient == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @GetMapping("/getAllIngredients")
    @Operation(
            summary = "Получение ингредиентов",
            description = "Можно получить все имеющиеся ингредиенты"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты найдены"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ни один ингредиент не найден"
            )
    })
    public ResponseEntity<Map<Integer, Ingredient>> getAllIngredients(){
        if (ingredientService.getAllIngredients() != null) {
            return ResponseEntity.ok().body(ingredientService.getAllIngredients());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @GetMapping("/getAllIngredients")
    @Operation(
            summary = "Редактирование ингредиента",
            description = "Можно изменить данные о ингредиенте по его id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент успешно изменен"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент по введенному id не найден, либо измененные " +
                            "данные введены некорректно"
            )
    })
    public ResponseEntity<Ingredient> editIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        Ingredient ingredient1 = ingredientService.editIngredient(id, ingredient);
        if (ingredient1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient1);
    }

    @DeleteMapping("/{id}")
    @GetMapping("/getAllIngredients")
    @Operation(
            summary = "Удаление ингредиента",
            description = "Можно удалить ингредиент по id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Такой ингредиент не найден"
            )
    })
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id){
        if (ingredientService.deleteIngredient(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
