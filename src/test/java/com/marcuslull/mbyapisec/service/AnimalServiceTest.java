package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.AnimalDto;
import com.marcuslull.mbyapisec.model.entity.Animal;
import com.marcuslull.mbyapisec.repository.AnimalRepository;
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
class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private MapperService mapperService;

    @Mock
    private CustomAuthenticationProviderService customAuthenticationProviderService;

    @InjectMocks
    private AnimalService animalService;

    @Test
    void getAnimals() {
        // Arrange
        Animal animal1 = new Animal();
        Animal animal2 = new Animal();
        List<Animal> animals = Arrays.asList(animal1, animal2);
        AnimalDto animalDto1 = new AnimalDto();
        AnimalDto animalDto2 = new AnimalDto();
        List<AnimalDto> animalDtos = Arrays.asList(animalDto1, animalDto2);
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(animalRepository.findAnimalsByOwner("owner")).thenReturn(animals);
        when(mapperService.map(animal1)).thenReturn(animalDto1);
        when(mapperService.map(animal2)).thenReturn(animalDto2);

        // Act
        List<AnimalDto> result = animalService.getAnimals();

        // Assert
        assertEquals(animalDtos, result);
    }

    @Test
    void getAnimalFound() {
        // Arrange
        Long id = 1L;
        Animal animal = new Animal();
        AnimalDto animalDto = new AnimalDto();
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(animalRepository.findAnimalByIdAndOwner(id, "owner")).thenReturn(animal);
        when(mapperService.map(animal)).thenReturn(animalDto);

        // Act
        AnimalDto result = animalService.getAnimal(id);

        // Assert
        assertEquals(animalDto, result);
    }

    @Test
    void getAnimalNotFound() {
        // Arrange
        Long id = 1L;
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(animalRepository.findAnimalByIdAndOwner(id, "owner")).thenReturn(null);

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> animalService.getAnimal(id));
    }

    @Test
    void postAnimalSuccess() {
        // Arrange
        AnimalDto inputAnimalDto = new AnimalDto();
        Animal animal = new Animal();
        AnimalDto outputAnimalDto = new AnimalDto();
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(mapperService.map(inputAnimalDto)).thenReturn(animal);
        when(animalRepository.save(animal)).thenReturn(animal);
        when(mapperService.map(animal)).thenReturn(outputAnimalDto);

        // Act
        AnimalDto result = animalService.postAnimal(inputAnimalDto);

        // Assert
        assertEquals(outputAnimalDto, result);
    }

    @Test
    void postAnimalFail() {
        // Arrange
        AnimalDto inputAnimalDto = new AnimalDto();
        Animal animal = new Animal();
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(mapperService.map(inputAnimalDto)).thenReturn(animal);
        when(animalRepository.save(animal)).thenThrow(RuntimeException.class);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> animalService.postAnimal(inputAnimalDto));
    }

    @Test
    void deleteAnimalSuccessOrFailure() {
        // Arrange
        Long id = 1L;
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        doNothing().when(animalRepository).deleteAnimalByIdAndOwner(id, "owner");

        // Act
        animalService.deleteAnimal(id);

        // Assert
        verify(animalRepository, times(1)).deleteAnimalByIdAndOwner(id, "owner");
    }
}