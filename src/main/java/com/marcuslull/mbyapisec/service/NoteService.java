package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.repository.NoteRepository;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final MapperService mapperService;
    private final NoteRepository noteRepository;

    public NoteService(MapperService mapperService, NoteRepository noteRepository) {
        this.mapperService = mapperService;
        this.noteRepository = noteRepository;
    }
}
