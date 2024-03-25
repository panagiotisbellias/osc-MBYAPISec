package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.model.dto.NoteDto;
import com.marcuslull.mbyapisec.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {
    // All NULLs are thrown to GlobalExceptionHandler at the service layer
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/api/yard/{id}/notes")
    public ResponseEntity<List<NoteDto>> getNotesForYard(@PathVariable Long id) {
        // NumberFormatException caught in GlobalExceptionHandler
        return ResponseEntity.ok(noteService.getNotesForYard(id));
    }

    @GetMapping("/api/note/{id}")
    public ResponseEntity<NoteDto> getNote(@PathVariable Long id) {
        // NumberFormatException caught in GlobalExceptionHandler
        return ResponseEntity.ok(noteService.getNote(id));
    }

    @PostMapping("/api/notes")
    public ResponseEntity<NoteDto> postNote(@RequestBody NoteDto noteDto) {
        // HttpMessageNotReadableException MethodArgumentTypeMismatchException caught in GlobalExceptionHandler
        return ResponseEntity.ok(noteService.postNote(noteDto));
    }

    @PutMapping("/api/note/{id}")
    public ResponseEntity<NoteDto> putNote(@PathVariable Long id, @RequestBody NoteDto noteDto) {
        // HttpMessageNotReadableException MethodArgumentTypeMismatchException NumberFormatException caught in GlobalExceptionHandler
        return ResponseEntity.ok(noteService.putNote(id, noteDto));
    }

    @DeleteMapping("/api/note/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
