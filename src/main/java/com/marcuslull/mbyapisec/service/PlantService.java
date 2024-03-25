package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.PlantDto;
import com.marcuslull.mbyapisec.model.entity.Plant;
import com.marcuslull.mbyapisec.repository.PlantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PlantService {
    private final PlantRepository plantRepository;
    private final MapperService mapperService;
    private final CustomAuthenticationProviderService customAuthenticationProviderService;

    public PlantService(PlantRepository plantRepository, MapperService mapperService, CustomAuthenticationProviderService customAuthenticationProviderService) {
        this.plantRepository = plantRepository;
        this.mapperService = mapperService;
        this.customAuthenticationProviderService = customAuthenticationProviderService;
    }

    public List<PlantDto> getPlants() {
        List<PlantDto> plantDtos = new ArrayList<>();
        plantRepository.findPlantsByOwner(customAuthenticationProviderService.getAuthenticatedName())
                .forEach(plant -> {
                    PlantDto plantDto = mapperService.map(plant);
                    plantDtos.add(plantDto);
                });
        return plantDtos;
    }

    public PlantDto postPlant(PlantDto plantDto) {
        plantDto.setOwner(customAuthenticationProviderService.getAuthenticatedName());
        return mapperService.map(plantRepository.save(mapperService.map(plantDto)));
    }

    public PlantDto getPlant(Long id) {
        Plant plant = plantRepository.findPlantByIdAndOwner(id, customAuthenticationProviderService.getAuthenticatedName());
        if (plant != null) {
            return mapperService.map(plant);
        } else throw new NoSuchElementException();
    }

    @Transactional
    public void deletePlant(Long id) {
        plantRepository.deletePlantByIdAndOwner(id, customAuthenticationProviderService.getAuthenticatedName());
    }
}
