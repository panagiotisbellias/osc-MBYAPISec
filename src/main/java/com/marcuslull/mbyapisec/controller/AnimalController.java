package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.model.dto.AnimalDto;
import com.marcuslull.mbyapisec.model.entity.Animal;
import com.marcuslull.mbyapisec.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnimalController {
    // All NULLs are thrown to GlobalExceptionHandler at the service layer
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/api/animals")
    public ResponseEntity<List<AnimalDto>> getAnimals() {
        return ResponseEntity.ok(animalService.getAnimals());
    }

    @GetMapping("/api/animal/{id}")
    public ResponseEntity<AnimalDto> getAnimal(@PathVariable String id) {
        // NumberFormatException caught in GlobalExceptionHandler
        AnimalDto animalDto = animalService.getAnimal(Long.valueOf(id));
        return ResponseEntity.ok(animalDto);
    }

    @PostMapping("/api/animals")
    public ResponseEntity<AnimalDto> postAnimals(@RequestBody AnimalDto animalDto) {
        // HttpMessageNotReadableException MethodArgumentTypeMismatchException caught in GlobalExceptionHandler
        return ResponseEntity.ok(animalService.postAnimal(animalDto));
    }

    @DeleteMapping("/api/animal/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable String id) {
        // NumberFormatException caught in GlobalExceptionHandler
        animalService.deleteAnimal(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }
}
