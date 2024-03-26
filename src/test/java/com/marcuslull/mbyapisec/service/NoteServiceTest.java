package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.NoteDto;
import com.marcuslull.mbyapisec.model.entity.Note;
import com.marcuslull.mbyapisec.model.entity.Yard;
import com.marcuslull.mbyapisec.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private MapperService mapperService;

    @Mock
    private CustomAuthenticationProviderService customAuthenticationProviderService;

    @InjectMocks
    private NoteService noteService;

    @Test
    void getNotesForYard() {
        // Arrange
        Long yardId = 1L;
        Note note1 = new Note();
        Note note2 = new Note();
        NoteDto noteDto1 = new NoteDto();
        NoteDto noteDto2 = new NoteDto();
        List<NoteDto> noteDtos = Arrays.asList(noteDto1, noteDto2);
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(noteRepository.findNotesByOwnerAndYardId("owner", yardId)).thenReturn(Arrays.asList(note1, note2));
        // lenient() is used to suppress intentional stubbing of the same method
        lenient().when(mapperService.map(note1)).thenReturn(noteDto1);
        lenient().when(mapperService.map(note2)).thenReturn(noteDto2);

        // Act
        List<NoteDto> result = noteService.getNotesForYard(yardId);

        // Assert
        assertEquals(noteDtos, result);
    }

    @Test
    void getNoteFound() {
        // Arrange
        Long id = 1L;
        Note note = new Note();
        NoteDto noteDto = new NoteDto();
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(noteRepository.findNoteByOwnerAndId("owner", id)).thenReturn(note);
        when(mapperService.map(note)).thenReturn(noteDto);

        // Act
        NoteDto result = noteService.getNote(id);

        // Assert
        assertEquals(noteDto, result);
    }

    @Test
    void getNoteNotFound() {
        // Arrange
        Long id = 1L;
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(noteRepository.findNoteByOwnerAndId("owner", id)).thenReturn(null);

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> noteService.getNote(id));
    }

    @Test
    void postNoteSuccess() {
        // Arrange
        NoteDto inputNoteDto = new NoteDto();
        Note note = new Note();
        NoteDto outputNoteDto = new NoteDto();
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(mapperService.map(inputNoteDto)).thenReturn(note);
        when(noteRepository.save(note)).thenReturn(note);
        when(mapperService.map(note)).thenReturn(outputNoteDto);

        // Act
        NoteDto result = noteService.postNote(inputNoteDto);

        // Assert
        assertEquals(outputNoteDto, result);
    }

    @Test
    void putNoteSuccess() {
        // Arrange
        Long noteId = 1L;
        NoteDto inputNoteDto = new NoteDto();
        Note oldNote = new Note();
        oldNote.setId(noteId);
        Yard yard = new Yard();
        yard.setId(noteId);
        oldNote.setYard(yard);
        NoteDto outputNoteDto = new NoteDto();
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(noteRepository.findNoteByOwnerAndId("owner", noteId)).thenReturn(oldNote);
        when(mapperService.map(inputNoteDto)).thenReturn(oldNote);
        when(noteRepository.save(oldNote)).thenReturn(oldNote);
        when(mapperService.map(oldNote)).thenReturn(outputNoteDto);

        // Act
        NoteDto result = noteService.putNote(noteId, inputNoteDto);

        // Assert
        assertEquals(outputNoteDto, result);
    }

    @Test
    void deleteNoteFoundOrNotFound() {
        // Arrange
        Long id = 1L;
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");

        // Act
        noteService.deleteNote(id);

        // Assert
        verify(noteRepository, times(1)).deleteNoteByIdAndOwner(id, "owner");
    }
}