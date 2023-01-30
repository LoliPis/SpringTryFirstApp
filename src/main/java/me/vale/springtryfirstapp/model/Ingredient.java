package me.vale.springtryfirstapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Ingredient {
    private String name;
    private int count;
    private String unit;
}
