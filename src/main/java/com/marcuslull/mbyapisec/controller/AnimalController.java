package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.model.dto.AnimalDto;
import com.marcuslull.mbyapisec.model.entity.Animal;
import com.marcuslull.mbyapisec.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/api/animals")
    public ResponseEntity<List<AnimalDto>> getAnimals() {
        return ResponseEntity.ok(animalService.getAnimals()); // nulls handled in service
    }

    @GetMapping("/api/animal/{id}")
    public ResponseEntity<AnimalDto> getAnimal(@PathVariable String id) { // NumberFormatException caught in GlobalExceptionHandler
        AnimalDto animalDto = animalService.getAnimal(Long.valueOf(id)); // NumberFormatException caught in GlobalExceptionHandler
        if (animalDto == null) {
            return ResponseEntity.notFound().build(); // TODO: Exception possibility null
        }
        return ResponseEntity.ok(animalDto);
    }

    @PostMapping("/api/animals")
    public ResponseEntity<AnimalDto> postAnimals(@RequestBody AnimalDto animalDto) { // HttpMessageNotReadableException MethodArgumentTypeMismatchException caught in GlobalExceptionHandler
        return ResponseEntity.ok(animalService.postAnimal(animalDto)); // TODO: Exception possibility null
    }

    @DeleteMapping("/api/animal/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable String id) { // NumberFormatException caught in GlobalExceptionHandler
        animalService.deleteAnimal(Long.valueOf(id)); // TODO: Exception possibility NumberFormatException null
        return ResponseEntity.noContent().build();
    }
}
