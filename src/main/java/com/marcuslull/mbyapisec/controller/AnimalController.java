package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.model.dto.AnimalDto;
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
        return ResponseEntity.ok(animalService.getAnimals());
    }

    @GetMapping("/api/animal/{id}")
    public ResponseEntity<AnimalDto> getAnimal(@PathVariable String id) { // TODO: Exception possibility MethodArgumentConversionException
        AnimalDto animalDto = animalService.getAnimal(Long.valueOf(id)); // TODO: Exception possibility NumberFormatException null
        if (animalDto == null) {
            return ResponseEntity.notFound().build(); // TODO: Exception possibility null
        }
        return ResponseEntity.ok(animalDto);
    }

    @PostMapping("/api/animals")
    public ResponseEntity<AnimalDto> postAnimals(@RequestBody AnimalDto animalDto) { // TODO: Exception possibility HttpMessageNotReadableException MethodArgumentTypeMismatchException
        return ResponseEntity.ok(animalService.postAnimal(animalDto)); // TODO: Exception possibility null
    }

    @DeleteMapping("/api/animal/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable String id) { // TODO: Exception possibility MethodArgumentConversionException
        animalService.deleteAnimal(Long.valueOf(id)); // TODO: Exception possibility NumberFormatException null
        return ResponseEntity.noContent().build();
    }
}
