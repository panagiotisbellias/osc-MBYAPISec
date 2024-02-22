package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.YardDto;
import com.marcuslull.mbyapisec.model.entity.Animal;
import com.marcuslull.mbyapisec.model.entity.Plant;
import com.marcuslull.mbyapisec.model.entity.Yard;
import com.marcuslull.mbyapisec.repository.AnimalRepository;
import com.marcuslull.mbyapisec.repository.PlantRepository;
import com.marcuslull.mbyapisec.repository.UserRepository;
import com.marcuslull.mbyapisec.repository.YardRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MapperService {

    private final PlantRepository plantRepository;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final YardRepository yardRepository;

    public MapperService(PlantRepository plantRepository, AnimalRepository animalRepository, UserRepository userRepository, YardRepository yardRepository) {
        this.plantRepository = plantRepository;
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
        this.yardRepository = yardRepository;
    }

    public <T> T map(Object source) {

        return switch (source.getClass().getSimpleName()) {
            case "Yard", "YardDto" -> (T) mapYard(source);
            //case "Plant", "PlantDto" -> (T) mapPlant(source);
            //case "Animal", "AnimalDto" -> (T) mapAnimal(source);
            default -> null;
        };
    }

    private Object mapYard(Object source) {
        if (source instanceof Yard yard) {
            YardDto yardDto = new YardDto();
            yardDto.setId(yard.getId());
            yardDto.setCreated(yard.getCreated());
            yardDto.setUpdated(yard.getUpdated());
            yardDto.setName(yard.getName());
            yardDto.setHardinessZone(yard.getHardinessZone());
            yardDto.setYardSubType(yard.getYardSubType());
            yardDto.setPlantIds(yard.getPlants().stream().map(Plant::getId).collect(Collectors.toList()));
            yardDto.setAnimalIds(yard.getAnimals().stream().map(Animal::getId).collect(Collectors.toList()));
            yardDto.setUserId(yard.getUser().getId());
            return yardDto;
        } else {
            YardDto yardDto = (YardDto) source;
            Yard yard = new Yard();
            yard.setId(yardDto.getId());
            yard.setCreated(yardDto.getCreated());
            yard.setUpdated(yardDto.getUpdated());
            yard.setName(yardDto.getName());
            yard.setHardinessZone(yardDto.getHardinessZone());
            yard.setYardSubType(yardDto.getYardSubType());
            if (yardDto.getPlantIds() != null) {
                yard.setPlants(yardDto.getPlantIds().stream().map(plant -> plantRepository.findById(plant)
                        .get()).collect(Collectors.toList()));
            }
            if (yardDto.getAnimalIds() != null) {
                yard.setAnimals(yardDto.getAnimalIds().stream().map(animal -> animalRepository.findById(animal)
                        .get()).collect(Collectors.toList()));
            }
            yard.setUser(userRepository.findById(yardDto.getUserId()).get());
            return yard;
        }
    }
}
