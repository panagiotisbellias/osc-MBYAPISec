package com.marcuslull.mbyapisec.repository;

import com.marcuslull.mbyapisec.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

public interface NoteRepository extends JpaRepository<Note, Long> {
    @PreAuthorize("#email == authentication.name")
    Iterable<Object> findNotesByOwnerAndYardId(String email, Long yardId);

    @PreAuthorize("#email == authentication.name")
    Note findNoteByOwnerAndId(String email, Long noteId);
}