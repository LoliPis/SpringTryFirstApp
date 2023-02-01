package me.vale.springtryfirstapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private String name;
    private int timeOfPreparing;
    private LinkedList<Ingredient> ingredients;
    private LinkedList<String> steps;

}
