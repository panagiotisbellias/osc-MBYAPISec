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
    public ResponseEntity<List<NoteDto>> getNotesForYard(@PathVariable String id) { // TODO: Exception possibility MethodArgumentConversionException
        return ResponseEntity.ok(noteService.getNotesForYard(id)); // TODO: Exception possibility null
    }

    @GetMapping("/api/note/{id}")
    public ResponseEntity<NoteDto> getNote(@PathVariable String id) { // TODO: Exception possibility MethodArgumentConversionException
        NoteDto returnedNoteDto = noteService.getNote(id); // TODO: Exception possibility null
        if (returnedNoteDto == null) {
            return ResponseEntity.notFound().build(); // TODO: Exception possibility null
        }
        return ResponseEntity.ok(noteService.getNote(id)); // TODO: Exception possibility null
    }

    @PostMapping("/api/notes")
    public ResponseEntity<NoteDto> postNote(@RequestBody NoteDto noteDto) { // TODO: Exception possibility HttpMessageNotReadableException MethodArgumentTypeMismatchException
        return ResponseEntity.ok(noteService.postNote(noteDto)); // TODO: Exception possibility null
    }

    @PutMapping("/api/note/{id}")
    public ResponseEntity<NoteDto> putNote(@PathVariable String id, @RequestBody NoteDto noteDto) { // TODO: Exception possibility HttpMessageNotReadableException MethodArgumentTypeMismatchException
        NoteDto returnedNoteDto = noteService.putNote(id, noteDto);
        if (returnedNoteDto == null) { // TODO: Exception possibility null
            ResponseEntity.notFound().build(); // TODO: Exception possibility null
        }
        return ResponseEntity.ok(returnedNoteDto);
    }

    @DeleteMapping("/api/note/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable String id) { // TODO: Exception possibility MethodArgumentConversionException
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
