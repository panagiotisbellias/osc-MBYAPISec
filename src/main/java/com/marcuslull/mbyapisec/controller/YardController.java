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
        return ResponseEntity.ok(yardService.getYards());
    }

    @GetMapping("/api/yard/{id}")
    public ResponseEntity<YardDto> getYard(@PathVariable String id) {
        YardDto yardDto = yardService.getYard(Long.valueOf(id));
        if (yardDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(yardDto);
    }

    @PostMapping("/api/yards")
    public ResponseEntity<YardDto> postYards(@RequestBody YardDto yardDto) {
        return ResponseEntity.ok(yardService.postYard(yardDto));
    }

    @PutMapping("/api/yard/{id}")
    public ResponseEntity<YardDto> putYard(@PathVariable String id, @RequestBody YardDto yardDto) {
        YardDto returnedYard = yardService.putYard(id, yardDto);
        if (returnedYard != null) {
            return ResponseEntity.ok(returnedYard);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/yard/{id}")
    public ResponseEntity<String> deleteYard(@PathVariable String id) {
        yardService.deleteYard(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }
}
