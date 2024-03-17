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
}
