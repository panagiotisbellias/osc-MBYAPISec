package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.model.dto.PlantDto;
import com.marcuslull.mbyapisec.service.PlantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlantController {
    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping("/api/plants")
    public ResponseEntity<List<PlantDto>> getPlants() {
        return ResponseEntity.ok(plantService.getPlants()); // TODO: Exception possibility null
    }

    @GetMapping("/api/plant/{id}")
    public ResponseEntity<PlantDto> getPlant(@PathVariable String id) { // NumberFormatException caught in GlobalExceptionHandler
        PlantDto plantDto = plantService.getPlant(Long.valueOf(id)); // NumberFormatException caught in GlobalExceptionHandler
        if (plantDto == null) {
            return ResponseEntity.notFound().build(); // TODO: Exception possibility null
        }
        return ResponseEntity.ok(plantDto);
    }

    @PostMapping("/api/plants")
    public ResponseEntity<PlantDto> postPlants(@RequestBody PlantDto plantDto) { // HttpMessageNotReadableException MethodArgumentTypeMismatchException caught in GlobalExceptionHandler
        return ResponseEntity.ok(plantService.postPlant(plantDto)); // TODO: Exception possibility null
    }

    @DeleteMapping("/api/plant/{id}")
    public ResponseEntity<String> deletePlant(@PathVariable String id) { // NumberFormatException caught in GlobalExceptionHandler
        plantService.deletePlant(Long.valueOf(id)); // TODO: Exception possibility NumberFormatException null
        return ResponseEntity.noContent().build();
    }
}
