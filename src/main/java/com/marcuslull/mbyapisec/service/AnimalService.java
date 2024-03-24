package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.AnimalDto;
import com.marcuslull.mbyapisec.model.entity.Animal;
import com.marcuslull.mbyapisec.repository.AnimalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final MapperService mapperService;
    private final CustomAuthenticationProviderService customAuthenticationProviderService;

    public AnimalService(AnimalRepository animalRepository,
                         MapperService mapperService,
                         CustomAuthenticationProviderService customAuthenticationProviderService) {
        this.animalRepository = animalRepository;
        this.mapperService = mapperService;
        this.customAuthenticationProviderService = customAuthenticationProviderService;
    }

    public List<AnimalDto> getAnimals() {
        List<AnimalDto> animalDtos = new ArrayList<>();
        animalRepository.findAnimalsByOwner(customAuthenticationProviderService.getAuthenticatedName())
                .forEach(animal -> {
                    AnimalDto animalDto = mapperService.map(animal);
                    animalDtos.add(animalDto);
                });
        return animalDtos;
    }

    public AnimalDto postAnimal(AnimalDto animalDto) {
        animalDto.setOwner(customAuthenticationProviderService.getAuthenticatedName());
        // IllegalArgumentException caught in GlobalExceptionHandler
        return mapperService.map(animalRepository.save(mapperService.map(animalDto)));
    }

    public AnimalDto getAnimal(Long id) {
        Animal animal = animalRepository.findAnimalByIdAndOwner(id,
                customAuthenticationProviderService.getAuthenticatedName());
        if (animal != null) {
            return mapperService.map(animal);
        } else throw new RuntimeException(); // TODO: Custom Exception
    }

    @Transactional
    public void deleteAnimal(Long id) {
        animalRepository.deleteAnimalByIdAndOwner(id, customAuthenticationProviderService.getAuthenticatedName());
    }
}
