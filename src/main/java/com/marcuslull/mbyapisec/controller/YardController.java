package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.model.dto.YardDto;
import com.marcuslull.mbyapisec.service.YardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class YardController {
    private final YardService yardService;

    public YardController(YardService yardService) {
        this.yardService = yardService;
    }

    @GetMapping("/api/yards")
    public ResponseEntity<List<YardDto>> getYards() {
        return ResponseEntity.ok(yardService.getYards()); // TODO: Exception possibility null
    }

    @GetMapping("/api/yard/{id}")
    public ResponseEntity<YardDto> getYard(@PathVariable String id) { // TODO: Exception possibility MethodArgumentConversionException
        YardDto yardDto = yardService.getYard(Long.valueOf(id)); // TODO: Exception possibility NumberFormatException null
        if (yardDto == null) {
            return ResponseEntity.notFound().build(); // TODO: Exception possibility null
        }
        return ResponseEntity.ok(yardDto);
    }

    @PostMapping("/api/yards")
    public ResponseEntity<YardDto> postYards(@RequestBody YardDto yardDto) { // TODO: Exception possibility HttpMessageNotReadableException MethodArgumentTypeMismatchException
        return ResponseEntity.ok(yardService.postYard(yardDto)); // TODO: Exception possibility null
    }

    @PutMapping("/api/yard/{id}")
    public ResponseEntity<YardDto> putYard(@PathVariable String id, @RequestBody YardDto yardDto) { // TODO: Exception possibility MethodArgumentConversionException
        YardDto returnedYard = yardService.putYard(id, yardDto); // TODO: Exception possibility null
        if (returnedYard != null) {
            return ResponseEntity.ok(returnedYard);
        }
        return ResponseEntity.notFound().build(); // TODO: Exception possibility null
    }

    @DeleteMapping("/api/yard/{id}")
    public ResponseEntity<String> deleteYard(@PathVariable String id) { // TODO: Exception possibility MethodArgumentConversionException
        yardService.deleteYard(Long.valueOf(id)); // TODO: Exception possibility NumberFormatException null
        return ResponseEntity.noContent().build();
    }
}
