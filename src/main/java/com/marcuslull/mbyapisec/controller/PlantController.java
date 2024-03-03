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
        return ResponseEntity.ok(plantService.getPlants());
    }

    @GetMapping("/api/plant/{id}")
    public ResponseEntity<PlantDto> getPlant(@PathVariable String id) {
        PlantDto plantDto = plantService.getPlant(Long.valueOf(id));
        if (plantDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(plantDto);
    }

    @PostMapping("/api/plants")
    public ResponseEntity<PlantDto> postPlants(@RequestBody PlantDto plantDto) {
        return ResponseEntity.ok(plantService.postPlant(plantDto));
    }

    @DeleteMapping("/api/plant/{id}")
    public ResponseEntity<String> deletePlant(@PathVariable String id) {
        plantService.deletePlant(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }
}
