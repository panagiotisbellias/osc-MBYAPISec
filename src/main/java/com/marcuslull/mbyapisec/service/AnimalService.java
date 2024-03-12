package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.AnimalDto;
import com.marcuslull.mbyapisec.model.entity.Animal;
import com.marcuslull.mbyapisec.repository.AnimalRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final MapperService mapperService;

    public AnimalService(AnimalRepository animalRepository, MapperService mapperService) {
        this.animalRepository = animalRepository;
        this.mapperService = mapperService;
    }

    public List<AnimalDto> getAnimals() {
        List<AnimalDto> animalDtos = new ArrayList<>();
        animalRepository.findAnimalsByOwner(SecurityContextHolder.getContext().getAuthentication().getName())
                .forEach(animal -> {
                    AnimalDto animalDto = mapperService.map(animal);
                    animalDtos.add(animalDto);
                });
        return animalDtos;
    }

    public AnimalDto postAnimal(AnimalDto animalDto) {
        animalDto.setOwner(SecurityContextHolder.getContext().getAuthentication().getName());
        return mapperService.map(animalRepository.save(mapperService.map(animalDto)));
    }

    public AnimalDto getAnimal(Long id) {
        Animal animal = animalRepository.findAnimalByIdAndOwner(id, SecurityContextHolder.getContext().getAuthentication().getName());
        if (animal != null) {
            return mapperService.map(animal);
        }
        return null;
    }

    @Transactional
    public void deleteAnimal(Long id) {
        animalRepository.deleteAnimalByIdAndOwner(id, SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
