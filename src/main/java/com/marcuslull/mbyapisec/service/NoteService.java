package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.NoteDto;
import com.marcuslull.mbyapisec.model.entity.Note;
import com.marcuslull.mbyapisec.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    private final MapperService mapperService;
    private final NoteRepository noteRepository;
    private final CustomAuthenticationProviderService customAuthenticationProviderService;

    public NoteService(MapperService mapperService, NoteRepository noteRepository,
                       CustomAuthenticationProviderService customAuthenticationProviderService) {
        this.mapperService = mapperService;
        this.noteRepository = noteRepository;
        this.customAuthenticationProviderService = customAuthenticationProviderService;
    }

    public List<NoteDto> getNotesForYard(String yardId) {
        List<NoteDto> noteDtos = new ArrayList<>();
        noteRepository.findNotesByOwnerAndYardId(customAuthenticationProviderService.getAuthenticatedName(),
                        Long.valueOf(yardId)) // NumberFormatException caught in the GlobalExceptionHandler
                .forEach(note -> {
                    NoteDto noteDto = mapperService.map(note);
                    noteDtos.add(noteDto);
                });
        return noteDtos;
    }

    public NoteDto getNote(String id) {
        Note note = noteRepository.findNoteByOwnerAndId(customAuthenticationProviderService.getAuthenticatedName(),
                Long.valueOf(id)); // NumberFormatException caught in the GlobalExceptionHandler
        if (note != null) {
            return mapperService.map(note);
        } else throw new RuntimeException(); //TODO: Custom Exception
    }

    public NoteDto postNote(NoteDto noteDto) {
        noteDto.setOwner(customAuthenticationProviderService.getAuthenticatedName());
        return mapperService.map(noteRepository.save(mapperService.map(noteDto)));
    }

    public NoteDto putNote(String noteId, NoteDto noteDto) {
        // it's just the comment that might be changing - this can also double as a PATCH
        noteDto.setId(Long.valueOf(noteId)); // NumberFormatException caught in the GlobalExceptionHandler
        noteDto.setOwner(customAuthenticationProviderService.getAuthenticatedName());
        Note oldNote = noteRepository.findNoteByOwnerAndId(noteDto.getOwner(), noteDto.getId());
        if (oldNote != null) {
            noteDto.setYardId(oldNote.getYard().getId());
            noteDto.setCreated(oldNote.getCreated());
            return mapperService.map(noteRepository.save(mapperService.map(noteDto)));
        } else throw new RuntimeException(); //TODO: Custom Exception
    }

    @Transactional
    public void deleteNote(String id) {
        // NumberFormatException caught in the GlobalExceptionHandler
        noteRepository.deleteNoteByIdAndOwner(Long.valueOf(id), customAuthenticationProviderService.getAuthenticatedName());
    }
}
