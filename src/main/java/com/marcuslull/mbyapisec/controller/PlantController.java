package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.model.dto.PlantDto;
import com.marcuslull.mbyapisec.service.PlantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlantController {
    // All NULLs are thrown to GlobalExceptionHandler at the service layer
    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping("/api/plants")
    public ResponseEntity<List<PlantDto>> getPlants() {
        return ResponseEntity.ok(plantService.getPlants());
    }

    @GetMapping("/api/plant/{id}")
    public ResponseEntity<PlantDto> getPlant(@PathVariable Long id) {
        // NumberFormatException caught in GlobalExceptionHandler
        return ResponseEntity.ok(plantService.getPlant(id));
    }

    @PostMapping("/api/plants")
    public ResponseEntity<PlantDto> postPlants(@RequestBody PlantDto plantDto) {
        // HttpMessageNotReadableException MethodArgumentTypeMismatchException caught in GlobalExceptionHandler
        return ResponseEntity.ok(plantService.postPlant(plantDto));
    }

    @DeleteMapping("/api/plant/{id}")
    public ResponseEntity<String> deletePlant(@PathVariable Long id) {
        plantService.deletePlant(id);
        return ResponseEntity.noContent().build();
    }
}
