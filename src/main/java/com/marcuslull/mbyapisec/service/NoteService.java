package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.NoteDto;
import com.marcuslull.mbyapisec.model.entity.Note;
import com.marcuslull.mbyapisec.repository.NoteRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    private final MapperService mapperService;
    private final NoteRepository noteRepository;

    public NoteService(MapperService mapperService, NoteRepository noteRepository) {
        this.mapperService = mapperService;
        this.noteRepository = noteRepository;
    }

    public List<NoteDto> getNotesForYard(String yardId) {
        List<NoteDto> noteDtos = new ArrayList<>();
        noteRepository.findNotesByOwnerAndYardId(SecurityContextHolder.getContext().getAuthentication().getName(),
                        Long.valueOf(yardId)) // TODO: Exception possibility NumberFormatException
                .forEach(note -> {
                    NoteDto noteDto = mapperService.map(note);
                    noteDtos.add(noteDto);
                });
        return noteDtos;
    }

    public NoteDto getNote(String id) {
        return mapperService.map(noteRepository.findNoteByOwnerAndId(SecurityContextHolder.getContext().getAuthentication().getName(),
                Long.valueOf(id))); // TODO: Exception possibility NumberFormatException
    }

    public NoteDto postNote(NoteDto noteDto) {
        noteDto.setOwner(SecurityContextHolder.getContext().getAuthentication().getName()); // TODO: Exception possibility null
        return mapperService.map(noteRepository.save(mapperService.map(noteDto))); // TODO: Exception possibility null
    }

    public NoteDto putNote(String noteId, NoteDto noteDto) {
        // it's just the comment that might be changing - this can also double as a PATCH
        noteDto.setId(Long.valueOf(noteId)); // TODO: Exception possibility NumberFormatException
        noteDto.setOwner(SecurityContextHolder.getContext().getAuthentication().getName()); // TODO: Exception possibility null
        Note oldNote = noteRepository.findNoteByOwnerAndId(noteDto.getOwner(), noteDto.getId()); // TODO: Exception possibility null
        if (oldNote != null) {
            noteDto.setYardId(oldNote.getYard().getId());
            noteDto.setCreated(oldNote.getCreated());
            return mapperService.map(noteRepository.save(mapperService.map(noteDto))); // TODO: Exception possibility IllegalArgumentException
        }
        return null; // TODO: Exception possibility null
    }

    @Transactional
    public void deleteNote(String id) {
        if (noteRepository.existsById(Long.valueOf(id))) { // TODO: Exception possibility NumberFormatException
            noteRepository.deleteNoteByIdAndOwner(Long.valueOf(id), SecurityContextHolder.getContext().getAuthentication().getName()); // TODO: Exception possibility null NumberFormatException
        }
    }
}
