package me.vale.springtryfirstapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.vale.springtryfirstapp.services.FilesService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;

@RestController
@RequestMapping("/files")
@Tag(name = "Импорт/экспорт файлов", description = "Контроллер позволяет делать импорт/экспорт фалов рецептов " +
        "и экспорт файлов ингредиентов")
public class FilesController {

    @Value("${name.of.ingredients.file}")
    private String ingredientFileName;

    @Value("${name.of.recipes.file}")
    private String recipeFileName;

    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping("/ingredientsExport")
    @Operation(
            summary = "Экспорт файла с ингредиентами",
            description = "Можно экспортровать все ингредиенты в файл формата json"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл скачен"
            )
    })
    private ResponseEntity<InputStreamResource> downloadIngredientsFile() throws FileNotFoundException {
        File ingredientsFile = filesService.getDataFile(ingredientFileName);
        if (ingredientsFile.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(ingredientsFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(ingredientsFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Ingredients.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/recipesExport")
    @Operation(
            summary = "Экспорт файла с рецептами",
            description = "Можно экспортровать все рецепты в файл формата json"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл скачен"
            )
    })
    private ResponseEntity<InputStreamResource> downloadRecipeFile() throws FileNotFoundException {
        File recipesFile = filesService.getDataFile(recipeFileName);
        if (recipesFile.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(recipesFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(recipesFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Recipes.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/recipesImport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Импорт файла с рецептами",
            description = "Можно Импортировать файл рецептов в формате json"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл импортирован"
            )
    })
    public ResponseEntity<Void> uploadRecipesFile(@RequestParam MultipartFile file) {
        filesService.cleanDataFile(recipeFileName);
        File recipesFile = filesService.getDataFile(recipeFileName);
        try (FileOutputStream fos = new FileOutputStream(recipesFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
