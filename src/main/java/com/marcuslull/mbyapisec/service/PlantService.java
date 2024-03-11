package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.PlantDto;
import com.marcuslull.mbyapisec.model.entity.Plant;
import com.marcuslull.mbyapisec.repository.PlantRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlantService {
    private final PlantRepository plantRepository;
    private final MapperService mapperService;

    public PlantService(PlantRepository plantRepository, MapperService mapperService) {
        this.plantRepository = plantRepository;
        this.mapperService = mapperService;
    }

    public List<PlantDto> getPlants() {
        List<PlantDto> plantDtos = new ArrayList<>();
        plantRepository.findPlantsByOwner(SecurityContextHolder.getContext().getAuthentication().getName())
                .forEach(plant -> {
                    PlantDto plantDto = mapperService.map(plant);
                    plantDtos.add(plantDto);
                });
        return plantDtos;
    }

    public PlantDto postPlant(PlantDto plantDto) {
        plantDto.setOwner(SecurityContextHolder.getContext().getAuthentication().getName());
        return mapperService.map(plantRepository.save(mapperService.map(plantDto)));
    }

    public PlantDto getPlant(Long id) {
        Plant plant = plantRepository.findPlantByIdAndOwner(id, SecurityContextHolder.getContext().getAuthentication().getName());
        if (plant != null) {
            return mapperService.map(plant);
        }
        return null;
    }

    @Transactional
    public void deletePlant(Long id) {
        plantRepository.deletePlantByIdAndOwner(id, SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
