package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.YardDto;
import com.marcuslull.mbyapisec.model.entity.Animal;
import com.marcuslull.mbyapisec.model.entity.Note;
import com.marcuslull.mbyapisec.model.entity.Plant;
import com.marcuslull.mbyapisec.model.entity.Yard;
import com.marcuslull.mbyapisec.repository.YardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class YardServiceTest {

    @Mock
    private YardRepository yardRepository;

    @Mock
    private MapperService mapperService;

    @Mock
    private CustomAuthenticationProviderService customAuthenticationProviderService;

    @InjectMocks
    private YardService yardService;

    @Test
    void getYards() {
        // Arrange
        YardDto yardDto1 = new YardDto();
        YardDto yardDto2 = new YardDto();
        List<YardDto> yardDtoList = List.of(yardDto1, yardDto2);
        Yard yard1 = new Yard();
        Yard yard2 = new Yard();
        List<Yard> yardList = List.of(yard1, yard2);
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(yardRepository.findYardsByUserEmail("owner")).thenReturn(yardList);
        lenient().when(mapperService.map(yard1)).thenReturn(yardDto1);
        lenient().when(mapperService.map(yard2)).thenReturn(yardDto2);

        // Act
        List<YardDto> result = yardService.getYards();

        // Assert
        assertEquals(yardDtoList, result);
    }

    @Test
    void getYardFound() {
        // Arrange
        Long yardId = 1L;
        YardDto yardDto = new YardDto();
        yardDto.setId(yardId);
        Yard yard = new Yard();
        yard.setId(yardId);
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(yardRepository.findYardByIdAndUserEmail(yardId, "owner")).thenReturn(yard);
        when(mapperService.map(yard)).thenReturn(yardDto);

        // Act
        YardDto result = yardService.getYard(yardId);

        // Assert
        assertEquals(yardDto, result);
    }

    @Test
    void getYardNotFound() {
        // Arrange
        Long yardId = 1L;
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(yardRepository.findYardByIdAndUserEmail(yardId, "owner")).thenReturn(null);

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> yardService.getYard(yardId));
    }

    @Test
    void postYardSuccess() {
        // Arrange
        YardDto yardDto = new YardDto();
        Yard yard = new Yard();
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(mapperService.map(yardDto)).thenReturn(yard);
        when(yardRepository.save(yard)).thenReturn(yard);
        when(mapperService.map(yard)).thenReturn(yardDto);

        // Act
        YardDto result = yardService.postYard(yardDto);

        //Assert
        assertEquals(yardDto, result);
    }

    @Test
    void putYardSuccess() {
        // Arrange
        Long yardId = 1L;
        YardDto inputYardDto = new YardDto();
        Yard oldYard = new Yard();
        oldYard.setId(yardId);
        Plant plant = new Plant();
        plant.setId(yardId);
        oldYard.setPlants(List.of(plant));
        Animal animal = new Animal();
        animal.setId(yardId);
        oldYard.setAnimals(List.of(animal));
        Note note = new Note();
        note.setId(yardId);
        oldYard.setNotes(List.of(note));
        YardDto outputYardDto = new YardDto();
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(yardRepository.findYardByIdAndUserEmail(yardId, "owner")).thenReturn(oldYard);
        when(mapperService.map(inputYardDto)).thenReturn(oldYard);
        when(yardRepository.save(oldYard)).thenReturn(oldYard);
        when(mapperService.map(oldYard)).thenReturn(outputYardDto);

        // Act
        YardDto result = yardService.putYard(yardId, inputYardDto);

        // Assert
        assertEquals(outputYardDto, result);
    }

    @Test
    void putYardFail() {
        // Arrange
        Long yardId = 1L;
        Yard oldYard = new Yard();
        YardDto yardDto = new YardDto();
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(yardRepository.findYardByIdAndUserEmail(yardId, "owner")).thenReturn(null);

        // Act & Assert
        assertThrowsExactly(NoSuchElementException.class, () -> yardService.putYard(yardId, yardDto));
    }

    @Test
    void deleteYardSuccessOrFail() {
        // Arrange
        Long yardId = 1L;
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        doNothing().when(yardRepository).deleteYardByIdAndUserEmail(yardId, "owner");

        // Act
        yardService.deleteYard(yardId);

        // Assert
        verify(yardRepository, times(1)).deleteYardByIdAndUserEmail(yardId, "owner");
    }
}