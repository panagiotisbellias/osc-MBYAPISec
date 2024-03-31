package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.*;
import com.marcuslull.mbyapisec.model.entity.*;
import com.marcuslull.mbyapisec.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MapperServiceTest {
    @Mock
    private PlantRepository plantRepository;
    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private YardRepository yardRepository;
    @Mock
    private NoteRepository noteRepository;
    @Mock
    private CustomAuthenticationProviderService customAuthenticationProviderService;

    @InjectMocks
    private MapperService mapperService;

    @Test
    void mapImageAndImageDto() {
        // Arrange
        Long id = 1L;
        Image image = new Image();
        image.setId(id);
        Yard yard = new Yard();
        yard.setId(id);
        image.setYard(yard);
        User user = new User();
        user.setId(id);

        ImageDto imageDto = new ImageDto();
        imageDto.setYardId(id);
        imageDto.setId(id);
        Yard yard1 = new Yard();
        yard1.setId(id);
        Image image1 = new Image();
        image1.setYard(yard);

        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(userRepository.findUserByEmail("owner")).thenReturn(user);
        when(yardRepository.findYardByIdAndUserEmail(1L, "owner")).thenReturn(yard);

        // Act
        ImageDto resultImageDto = mapperService.map(image);
        Image resultImage = mapperService.map(imageDto);


        // Assert
        assertAll(
                () -> assertEquals(id, resultImageDto.getId()),
                () -> assertEquals(id, resultImageDto.getYardId())
        );
        assertAll(
                () -> assertEquals(id, resultImage.getId()),
                () -> assertEquals(image1.getYard(), resultImage.getYard())
        );
        verify(userRepository, atLeastOnce()).findUserByEmail("owner");
        verify(yardRepository, times(1)).findYardByIdAndUserEmail(id, "owner");
    }

    @Test
    void mapImageNullCase() {
        // Arrange
        Long id = 1L;
        ImageDto imageDto = new ImageDto();
        imageDto.setYardId(id);
        imageDto.setOwnerId(id);
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(yardRepository.findYardByIdAndUserEmail(id, "owner")).thenReturn(null);

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> mapperService.map(imageDto));
    }

    @Test
    void mapNoteAndNoteDto() {
        // Arrange
        Long id = 1L;
        Yard yard = new Yard();
        yard.setId(id);
        NoteDto noteDto = new NoteDto();
        noteDto.setYardId(id);
        Note note = new Note();
        note.setYard(yard);
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(yardRepository.findYardByIdAndUserEmail(id, "owner")).thenReturn(yard);

        // Act
        Note resultNote = mapperService.map(noteDto);
        NoteDto resultNoteDto = mapperService.map(note);

        // Assert
        assertAll(
                () -> assertEquals(resultNoteDto.getId(), noteDto.getId()),
                () -> assertEquals(resultNoteDto.getYardId(), noteDto.getYardId())
        );
        assertAll(
                () -> assertEquals(resultNote.getId(), note.getId()),
                () -> assertEquals(resultNote.getYard(), note.getYard())
        );
        verify(yardRepository, times(1)).findYardByIdAndUserEmail(id, "owner");
    }

    @Test
    void mapNoteNullCase() {
        // Arrange
        Long id = 1L;
        NoteDto noteDto = new NoteDto();
        noteDto.setYardId(id);
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(yardRepository.findYardByIdAndUserEmail(id, "owner")).thenReturn(null);

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> mapperService.map(noteDto));
    }

    @Test
    void mapAnimalAndPlantDto() {
        // Arrange
        Long id = 1L;
        Animal animal = new Animal();
        Yard yard = new Yard();
        yard.setId(id);
        animal.setYard(yard);
        AnimalDto animalDto = new AnimalDto();
        animalDto.setYardId(id);
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(yardRepository.findYardByIdAndUserEmail(id, "owner")).thenReturn(yard);

        // Act
        AnimalDto resultAnimalDto = mapperService.map(animal);
        Animal resultAnimal = mapperService.map(animalDto);

        // Assert
        assertAll(
                () -> assertEquals(resultAnimalDto.getId(), animalDto.getId()),
                () -> assertEquals(resultAnimalDto.getYardId(), animalDto.getYardId())
        );
        assertAll(
                () -> assertEquals(resultAnimal.getId(), animal.getId()),
                () -> assertEquals(resultAnimal.getYard(), animal.getYard())
        );
    }

    @Test
    void mapAnimalNullCase() {
        // Arrange
        Long id = 1L;
        AnimalDto animalDto = new AnimalDto();
        animalDto.setYardId(id);
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(yardRepository.findYardByIdAndUserEmail(id, "owner")).thenReturn(null);

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> mapperService.map(animalDto));
    }

    @Test
    void mapPlantAndPlantDto() {
        // Arrange
        Long id = 1L;
        Plant plant = new Plant();
        Yard yard = new Yard();
        yard.setId(id);
        plant.setYard(yard);
        PlantDto plantDto = new PlantDto();
        plantDto.setYardId(id);
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(yardRepository.findYardByIdAndUserEmail(id, "owner")).thenReturn(yard);

        // Act
        PlantDto resultPlantDto = mapperService.map(plant);
        Plant resultPlant = mapperService.map(plantDto);

        // Assert
        assertAll(
                () -> assertEquals(resultPlantDto.getId(), plantDto.getId()),
                () -> assertEquals(resultPlantDto.getYardId(), plantDto.getYardId())
        );
        assertAll(
                () -> assertEquals(resultPlant.getId(), plant.getId()),
                () -> assertEquals(resultPlant.getYard(), plant.getYard())
        );
    }

    @Test
    void mapPlantNullCase() {
        // Arrange
        Long id = 1L;
        PlantDto plantDto = new PlantDto();
        plantDto.setYardId(id);
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(yardRepository.findYardByIdAndUserEmail(id, "owner")).thenReturn(null);

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> mapperService.map(plantDto));
    }

    @Test
    void mapYardAndYardDto() {
        // Arrange
        Long id = 1L;
        Yard yardFromDB = new Yard();
        yardFromDB.setId(id);

        YardDto yardDto = new YardDto();
        yardDto.setUserEmail("owner");
        yardDto.setPlantIds(List.of(id));
        yardDto.setAnimalIds(List.of(id));
        yardDto.setNoteIds(List.of(id));

        Yard yard = new Yard();
        User user = new User();
        user.setEmail("owner");
        yard.setUser(user);
        Plant plant = new Plant();
        plant.setId(id);
        yard.setPlants(List.of(plant));
        Animal animal = new Animal();
        animal.setId(id);
        yard.setAnimals(List.of(animal));
        Note note = new Note();
        note.setId(id);
        yard.setNotes(List.of(note));

        when(plantRepository.findById(id)).thenReturn(Optional.of(plant));
        when(animalRepository.findById(id)).thenReturn(Optional.of(animal));
        when(noteRepository.findById(id)).thenReturn(Optional.of(note));
        when(userRepository.findUserByEmail("owner")).thenReturn(user);

        // Act
        Yard yardResult = mapperService.map(yardDto);
        YardDto yardDtoResult = mapperService.map(yard);

        // Assert
        assertAll(
                () -> assertEquals(yardDtoResult.getUserEmail(), yardDto.getUserEmail()),
                () -> assertEquals(yardDtoResult.getAnimalIds(), yardDto.getAnimalIds()),
                () -> assertEquals(yardDtoResult.getPlantIds(), yardDto.getPlantIds()),
                () -> assertEquals(yardDtoResult.getNoteIds(), yardDto.getNoteIds())
        );
        assertAll(
                () -> assertEquals(yardResult.getUser(), yard.getUser()),
                () -> assertEquals(yardResult.getAnimals(), yard.getAnimals()),
                () -> assertEquals(yardResult.getPlants(), yard.getPlants()),
                () -> assertEquals(yardResult.getNotes(), yard.getNotes())
        );
        verify(userRepository, times(1)).findUserByEmail("owner");
    }

    @Test
    void mapYardNullCase() {
        // Arrange
        Long id = 1L;
        Yard yardFromDB = new Yard();
        yardFromDB.setId(id);

        YardDto yardDto = new YardDto();
        yardDto.setUserEmail("owner");
        yardDto.setPlantIds(List.of(id));
        yardDto.setAnimalIds(List.of(id));
        yardDto.setNoteIds(List.of(id));

        Yard yard = new Yard();
        User user = new User();
        user.setEmail("owner");
        yard.setUser(user);
        Plant plant = new Plant();
        plant.setId(id);
        yard.setPlants(List.of(plant));
        Animal animal = new Animal();
        animal.setId(id);
        yard.setAnimals(List.of(animal));
        Note note = new Note();
        note.setId(id);
        yard.setNotes(List.of(note));

        when(plantRepository.findById(id)).thenReturn(Optional.of(plant));
        when(animalRepository.findById(id)).thenReturn(Optional.of(animal));
        when(noteRepository.findById(id)).thenReturn(Optional.of(note));
        when(userRepository.findUserByEmail("owner")).thenReturn(null);

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> mapperService.map(yardDto));
    }

    @Test
    void mapDefault() {
        // Arrange
        User user = new User();

        // Act & Assert
        assertThrows(RuntimeException.class, () -> mapperService.map(user));
    }
}