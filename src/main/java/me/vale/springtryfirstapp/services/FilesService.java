package me.vale.springtryfirstapp.services;

import org.springframework.stereotype.Service;

public interface FilesService {

    boolean saveToFile(String json, String dataFileName);

    String readFromFile(String dataFileName);
}
