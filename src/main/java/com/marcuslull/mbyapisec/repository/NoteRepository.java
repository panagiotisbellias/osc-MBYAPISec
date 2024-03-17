package com.marcuslull.mbyapisec.repository;

import com.marcuslull.mbyapisec.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}