package com.marcuslull.mbyapisec.config;

import com.marcuslull.mbyapisec.model.Animal;
import com.marcuslull.mbyapisec.model.Plant;
import com.marcuslull.mbyapisec.model.User;
import com.marcuslull.mbyapisec.model.Yard;
import com.marcuslull.mbyapisec.model.enums.*;
import com.marcuslull.mbyapisec.repository.AnimalRepository;
import com.marcuslull.mbyapisec.repository.PlantRepository;
import com.marcuslull.mbyapisec.repository.UserRepository;
import com.marcuslull.mbyapisec.repository.YardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.marcuslull.mbyapisec.model.enums.YardSubType.*;

@Component
public class BootstrapData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final YardRepository yardRepository;
    private final PlantRepository plantRepository;
    private final AnimalRepository animalRepository;

    public BootstrapData(UserRepository userRepository, YardRepository yardRepository,
                         PlantRepository plantRepository,
                         AnimalRepository animalRepository) {
        this.userRepository = userRepository;
        this.yardRepository = yardRepository;
        this.plantRepository = plantRepository;
        this.animalRepository = animalRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setEmail("user@email.com");
        // use https://bcrypt-generator.com/
        user.setPassword("$2a$12$HCkrs1z9lygxZH5gNrLOD.YtHT6e/a09MaqBPuw22pVLMVYhlAgmC");
        user.setEnabled(true);

        Yard yard = new Yard();
        yard.setName("MyYard");
        yard.setHardinessZone(HardinessZone.ZONE_6);
        yard.setYardSubType(BACK_YARD);
        yard.setUser(user);

        Plant plant = new Plant();
        plant.setName("Rose");
        plant.setHardinessZone(HardinessZone.ZONE_6);
        plant.setPlantSubType(PlantSubType.FLOWER);
        plant.setSoilType(SoilType.LOAM);
        plant.setSunExposure(SunExposure.PARTIAL_SUN);
        plant.setNativeAreaType(NativeAreaType.PRAIRIE);
        plant.setWateringFrequency(WateringFrequency.EVERY_OTHER_DAY);
        plant.setYard(yard);

        Animal animal = new Animal();
        animal.setName("Dog");
        animal.setAnimalSubType(AnimalSubType.MAMMAL);
        animal.setDietType(DietType.OMNIVORE);
        animal.setNativeAreaType(NativeAreaType.GRASSLAND);
        animal.setYard(yard);

        userRepository.save(user);
        yardRepository.save(yard);
        plantRepository.save(plant);
        animalRepository.save(animal);
    }
}
