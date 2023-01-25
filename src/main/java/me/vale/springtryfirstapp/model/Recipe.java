package me.vale.springtryfirstapp.model;

import lombok.Data;

import java.util.LinkedList;
@Data
public class Recipe {
    private String name;
    private int timeOfPreparing;
    private LinkedList<Ingredient> ingredients;
    private LinkedList<String> steps;

}
