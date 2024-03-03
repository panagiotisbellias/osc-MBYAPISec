package com.marcuslull.mbyapisec;

import com.marcuslull.mbyapisec.model.entity.Animal;
import com.marcuslull.mbyapisec.model.entity.Plant;
import com.marcuslull.mbyapisec.model.entity.User;
import com.marcuslull.mbyapisec.model.entity.Yard;
import com.marcuslull.mbyapisec.model.enums.*;
import com.marcuslull.mbyapisec.repository.AnimalRepository;
import com.marcuslull.mbyapisec.repository.PlantRepository;
import com.marcuslull.mbyapisec.repository.UserRepository;
import com.marcuslull.mbyapisec.repository.YardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.marcuslull.mbyapisec.model.enums.YardSubType.BACK_YARD;

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
        user.setGrantedAuthority(List.of(new SimpleGrantedAuthority("USER")));

        User admin = new User();
        admin.setEmail("admin@email.com");
        admin.setPassword("$2a$12$HCkrs1z9lygxZH5gNrLOD.YtHT6e/a09MaqBPuw22pVLMVYhlAgmC");
        admin.setEnabled(true);
        admin.setGrantedAuthority(List.of(new SimpleGrantedAuthority("ADMIN")));
        userRepository.save(admin);

        Yard yard = new Yard();
        yard.setName("MyYard");
        yard.setHardinessZone(HardinessZone.ZONE_6);
        yard.setYardSubType(BACK_YARD);
        yard.setUser(user);

        Plant plant = new Plant();
        plant.setOwner("user@email.com");
        plant.setName("Rose");
        plant.setHardinessZone(HardinessZone.ZONE_6);
        plant.setPlantSubType(PlantSubType.FLOWER);
        plant.setSoilType(SoilType.LOAM);
        plant.setSunExposure(SunExposure.PARTIAL_SUN);
        plant.setNativeAreaType(NativeAreaType.PRAIRIE);
        plant.setWateringFrequency(WateringFrequency.EVERY_OTHER_DAY);
        plant.setYard(yard);

        Animal animal = new Animal();
        animal.setOwner("user@email.com");
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
