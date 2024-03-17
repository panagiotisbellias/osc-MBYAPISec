package com.marcuslull.mbyapisec.controller;

import com.marcuslull.mbyapisec.model.dto.NoteDto;
import com.marcuslull.mbyapisec.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/api/yard/{id}/notes")
    public ResponseEntity<List<NoteDto>> getNotesForYard(@PathVariable String id) {
        return ResponseEntity.ok(noteService.getNotesForYard(id));
    }

    @GetMapping("/api/note/{id}")
    public ResponseEntity<NoteDto> getNote(@PathVariable String id) {
        NoteDto returnedNoteDto = noteService.getNote(id);
        if (returnedNoteDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(noteService.getNote(id));
    }

    @PostMapping("/api/notes")
    public ResponseEntity<NoteDto> postNote(@RequestBody NoteDto noteDto) {
        return ResponseEntity.ok(noteService.postNote(noteDto));
    }

    @PutMapping("/api/note/{id}")
    public ResponseEntity<NoteDto> putNote(@PathVariable String id, @RequestBody NoteDto noteDto) {
        NoteDto returnedNoteDto = noteService.putNote(id, noteDto);
        if (returnedNoteDto == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(returnedNoteDto);
    }

    @DeleteMapping("/api/note/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable String id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
