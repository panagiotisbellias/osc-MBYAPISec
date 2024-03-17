package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.NoteDto;
import com.marcuslull.mbyapisec.model.entity.Note;
import com.marcuslull.mbyapisec.repository.NoteRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
                        Long.valueOf(yardId))
                .forEach(note -> {
                    NoteDto noteDto = mapperService.map(note);
                    noteDtos.add(noteDto);
                });
        return noteDtos;
    }

    public NoteDto getNote(String id) {
        return mapperService.map(noteRepository.findNoteByOwnerAndId(SecurityContextHolder.getContext().getAuthentication().getName(),
                Long.valueOf(id)));
    }

    public NoteDto postNote(NoteDto noteDto) {
        noteDto.setOwner(SecurityContextHolder.getContext().getAuthentication().getName());
        return mapperService.map(noteRepository.save(mapperService.map(noteDto)));
    }
}
