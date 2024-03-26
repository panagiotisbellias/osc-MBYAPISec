package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.PlantDto;
import com.marcuslull.mbyapisec.model.entity.Plant;
import com.marcuslull.mbyapisec.repository.PlantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlantServiceTest {

    @Mock
    private PlantRepository plantRepository;

    @Mock
    private MapperService mapperService;

    @Mock
    private CustomAuthenticationProviderService customAuthenticationProviderService;

    @InjectMocks
    private PlantService plantService;

    @Test
    void getPlants() {
        // Arrange
        Plant plant1 = new Plant();
        Plant plant2 = new Plant();
        List<Plant> plants = Arrays.asList(plant1, plant2);
        PlantDto plantDto1 = new PlantDto();
        PlantDto plantDto2 = new PlantDto();
        List<PlantDto> plantDtos = Arrays.asList(plantDto1, plantDto2);
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(plantRepository.findPlantsByOwner("owner")).thenReturn(plants);
        when(mapperService.map(plant1)).thenReturn(plantDto1);
        when(mapperService.map(plant2)).thenReturn(plantDto2);

        // Act
        List<PlantDto> result = plantService.getPlants();

        // Assert
        assertEquals(plantDtos, result);
    }

    @Test
    void getPlant_found() {
        // Arrange
        Long id = 1L;
        Plant plant = new Plant();
        PlantDto plantDto = new PlantDto();
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(plantRepository.findPlantByIdAndOwner(id, "owner")).thenReturn(plant);
        when(mapperService.map(plant)).thenReturn(plantDto);

        // Act
        PlantDto result = plantService.getPlant(id);

        // Assert
        assertEquals(plantDto, result);
    }

    @Test
    void getPlant_notFound() {
        // Arrange
        Long id = 1L;
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(plantRepository.findPlantByIdAndOwner(id, "owner")).thenReturn(null);

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> plantService.getPlant(id));
    }

    @Test
    void postPlant_success() {
        // Arrange
        PlantDto plantDto = new PlantDto();
        Plant plant = new Plant();
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(mapperService.map(plantDto)).thenReturn(plant);
        when(plantRepository.save(plant)).thenReturn(plant);
        when(mapperService.map(plant)).thenReturn(plantDto);

        // Act
        PlantDto result = plantService.postPlant(plantDto);

        // Assert
        assertEquals(plantDto, result);
    }

    @Test
    void postPlant_failure() {
        // Arrange
        PlantDto plantDto = new PlantDto();
        Plant plant = new Plant();
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        when(mapperService.map(plantDto)).thenReturn(plant);
        when(plantRepository.save(plant)).thenThrow(RuntimeException.class);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> plantService.postPlant(plantDto));
    }

    @Test
    void deletePlant_successOrFailure() {
        // Arrange
        Long id = 1L;
        when(customAuthenticationProviderService.getAuthenticatedName()).thenReturn("owner");
        doNothing().when(plantRepository).deletePlantByIdAndOwner(id, "owner");

        // Act
        plantService.deletePlant(id);

        // Assert
        verify(plantRepository, times(1)).deletePlantByIdAndOwner(id, "owner");
    }
}