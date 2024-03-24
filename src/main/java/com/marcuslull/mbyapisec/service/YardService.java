package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.YardDto;
import com.marcuslull.mbyapisec.model.entity.Yard;
import com.marcuslull.mbyapisec.repository.YardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class YardService {
    private final YardRepository yardRepository;
    private final MapperService mapperService;
    private final CustomAuthenticationProviderService customAuthenticationProviderService;

    public YardService(YardRepository yardRepository, MapperService mapperService, CustomAuthenticationProviderService customAuthenticationProviderService) {
        this.yardRepository = yardRepository;
        this.mapperService = mapperService;
        this.customAuthenticationProviderService = customAuthenticationProviderService;
    }

    public List<YardDto> getYards() {
        List<YardDto> yardDtos = new ArrayList<>();
        yardRepository.findYardsByUserEmail(customAuthenticationProviderService.getAuthenticatedName())
                .forEach(yard -> {
                    YardDto yardDto = mapperService.map(yard);
                    yardDtos.add(yardDto);
                });
        return yardDtos;
    }

    public YardDto getYard(Long id) {
        Yard yard = yardRepository.findYardByIdAndUserEmail(id, customAuthenticationProviderService.getAuthenticatedName());
        if (yard != null) {
            return mapperService.map(yard);
        } else throw new RuntimeException(); // TODO: Custom Exception
    }

    public YardDto postYard(YardDto yardDto) {
        yardDto.setUserEmail(customAuthenticationProviderService.getAuthenticatedName());
        return mapperService.map(yardRepository.save(mapperService.map(yardDto)));
    }

    public YardDto putYard(String id, YardDto yardDto) {
        yardDto.setId(Long.valueOf(id)); // NumberFormatException caught in the GlobalExceptionHandler
        yardDto.setUserEmail(customAuthenticationProviderService.getAuthenticatedName());
        Yard yard = yardRepository.findYardByIdAndUserEmail(yardDto.getId(), yardDto.getUserEmail());
        if (yard != null) {
            yardDto.setCreated(yard.getCreated());
            List<Long> plants = new ArrayList<>();
            yard.getPlants().forEach(plant -> plants.add(plant.getId()));
            yardDto.setPlantIds(plants);

            List<Long> animals = new ArrayList<>();
            yard.getAnimals().forEach(animal -> animals.add(animal.getId()));
            yardDto.setAnimalIds(animals);

            List<Long> notes = new ArrayList<>();
            yard.getNotes().forEach(note -> notes.add(note.getId()));
            yardDto.setNoteIds(notes);

            return mapperService.map(yardRepository.save(mapperService.map(yardDto)));
        } else throw new RuntimeException(); // TODO: Custom Exception
    }

    @Transactional
    public void deleteYard(Long id) {
        yardRepository.deleteYardByIdAndUserEmail(id, customAuthenticationProviderService.getAuthenticatedName());
    }
}
