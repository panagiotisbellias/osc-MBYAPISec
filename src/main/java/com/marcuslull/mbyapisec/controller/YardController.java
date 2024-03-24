package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.model.dto.YardDto;
import com.marcuslull.mbyapisec.service.YardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class YardController {
    // All NULLs are thrown to GlobalExceptionHandler at the service layer
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
        // NumberFormatException caught in GlobalExceptionHandler
        return ResponseEntity.ok(yardService.getYard(Long.valueOf(id)));
    }

    @PostMapping("/api/yards")
    public ResponseEntity<YardDto> postYards(@RequestBody YardDto yardDto) {
        // HttpMessageNotReadableException MethodArgumentTypeMismatchException caught in GlobalExceptionHandler
        return ResponseEntity.ok(yardService.postYard(yardDto));
    }

    @PutMapping("/api/yard/{id}")
    public ResponseEntity<YardDto> putYard(@PathVariable String id, @RequestBody YardDto yardDto) {
        // HttpMessageNotReadableException MethodArgumentConversionException NumberFormatException caught in GlobalExceptionHandler
        return ResponseEntity.ok(yardService.putYard(id, yardDto));
    }

    @DeleteMapping("/api/yard/{id}")
    public ResponseEntity<String> deleteYard(@PathVariable String id) {
        // NumberFormatException caught in GlobalExceptionHandler
        yardService.deleteYard(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }
}
