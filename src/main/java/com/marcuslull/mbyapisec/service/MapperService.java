package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.AnimalDto;
import com.marcuslull.mbyapisec.model.dto.NoteDto;
import com.marcuslull.mbyapisec.model.dto.PlantDto;
import com.marcuslull.mbyapisec.model.dto.YardDto;
import com.marcuslull.mbyapisec.model.entity.Animal;
import com.marcuslull.mbyapisec.model.entity.Note;
import com.marcuslull.mbyapisec.model.entity.Plant;
import com.marcuslull.mbyapisec.model.entity.Yard;
import com.marcuslull.mbyapisec.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MapperService {

    private final PlantRepository plantRepository;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final YardRepository yardRepository;
    private final NoteRepository noteRepository;

    public MapperService(PlantRepository plantRepository,
                         AnimalRepository animalRepository,
                         UserRepository userRepository,
                         YardRepository yardRepository,
                         NoteRepository noteRepository) {
        this.plantRepository = plantRepository;
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
        this.yardRepository = yardRepository;
        this.noteRepository = noteRepository;
    }

    public <T> T map(Object source) {

        return switch (source.getClass().getSimpleName()) {
            case "Yard", "YardDto" -> (T) mapYard(source);
            case "Plant", "PlantDto" -> (T) mapPlant(source);
            case "Animal", "AnimalDto" -> (T) mapAnimal(source);
            case "Note", "NoteDto" -> (T) mapNote(source);
            default -> null; // TODO: Exception possibility null
        };
    }

    private Object mapNote(Object source) {
        if (source instanceof Note note) {
            NoteDto noteDto = new NoteDto();
            noteDto.setId(note.getId());
            noteDto.setCreated(note.getCreated());
            noteDto.setUpdated(note.getUpdated());
            noteDto.setOwner(note.getOwner());
            noteDto.setComment(note.getComment());
            noteDto.setYardId(note.getYard().getId());
            return noteDto;
        } else {
            NoteDto noteDto = (NoteDto) source;
            Note note = new Note();
            note.setId(noteDto.getId());
            note.setCreated(noteDto.getCreated());
            note.setUpdated(noteDto.getUpdated());
            note.setComment(noteDto.getComment());
            if (noteDto.getOwner() == null) {
                note.setOwner(SecurityContextHolder.getContext().getAuthentication().getName()); // TODO: Exception possibility null
            } else note.setOwner(noteDto.getOwner());
            if (noteDto.getYardId() != null) {
                note.setYard(yardRepository.findYardByIdAndUserEmail(noteDto.getYardId(),
                        SecurityContextHolder.getContext().getAuthentication().getName())); // TODO: Exception possibility null
            }
            return note;
        }
    }

    private Object mapAnimal(Object source) {
        if (source instanceof Animal animal) {
            AnimalDto animalDto = new AnimalDto();
            animalDto.setId(animal.getId());
            animalDto.setCreated(animal.getCreated());
            animalDto.setUpdated(animal.getUpdated());
            animalDto.setOwner(animal.getOwner());
            animalDto.setName(animal.getName());
            animalDto.setAnimalSubType(animal.getAnimalSubType());
            animalDto.setDietType(animal.getDietType());
            animalDto.setNativeAreaType(animal.getNativeAreaType());
            animalDto.setYardId(animal.getYard().getId());
            return animalDto;
        } else {
            AnimalDto animalDto = (AnimalDto) source;
            Animal animal = new Animal();
            animal.setId(animalDto.getId());
            animal.setCreated(animalDto.getCreated());
            animal.setUpdated(animalDto.getUpdated());
            animal.setOwner(animalDto.getOwner());
            animal.setName(animalDto.getName());
            animal.setAnimalSubType(animalDto.getAnimalSubType());
            animal.setDietType(animalDto.getDietType());
            animal.setNativeAreaType(animalDto.getNativeAreaType());
            if (animalDto.getYardId() != null) {
                animal.setYard(yardRepository.findYardByIdAndUserEmail(animalDto.getYardId(),
                        SecurityContextHolder.getContext().getAuthentication().getName())); // TODO: Exception possibility null
            }
            return animal;
        }
    }

    private Object mapPlant(Object source) {
        if (source instanceof Plant plant) {
            PlantDto plantDto = new PlantDto();
            plantDto.setId(plant.getId());
            plantDto.setCreated(plant.getCreated());
            plantDto.setUpdated(plant.getUpdated());
            plantDto.setOwner(plant.getOwner());
            plantDto.setName(plant.getName());
            plantDto.setHardinessZone(plant.getHardinessZone());
            plantDto.setNativeAreaType(plant.getNativeAreaType());
            plantDto.setPlantSubType(plant.getPlantSubType());
            plantDto.setSoilType(plant.getSoilType());
            plantDto.setSunExposure(plant.getSunExposure());
            plantDto.setWateringFrequency(plant.getWateringFrequency());
            plantDto.setYardId(plant.getYard().getId());
            return plantDto;
        } else {
            PlantDto plantDto = (PlantDto) source;
            Plant plant = new Plant();
            plant.setId(plantDto.getId());
            plant.setCreated(plantDto.getCreated());
            plant.setUpdated(plantDto.getUpdated());
            plant.setOwner(plantDto.getOwner());
            plant.setName(plantDto.getName());
            plant.setHardinessZone(plantDto.getHardinessZone());
            plant.setNativeAreaType(plantDto.getNativeAreaType());
            plant.setPlantSubType(plantDto.getPlantSubType());
            plant.setSoilType(plantDto.getSoilType());
            plant.setSunExposure(plantDto.getSunExposure());
            plant.setWateringFrequency(plantDto.getWateringFrequency());
            if (plantDto.getYardId() != null) {
                plant.setYard(yardRepository.findYardByIdAndUserEmail(plantDto.getYardId(),
                        SecurityContextHolder.getContext().getAuthentication().getName())); // TODO: Exception possibility null
            }
            return plant;
        }
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
            yardDto.setNoteIds(yard.getNotes().stream().map(Note::getId).collect(Collectors.toList()));
            yardDto.setUserEmail(yard.getUser().getEmail());
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
                        .get()).collect(Collectors.toList())); // TODO: Exception possibility NoSuchElementException
            }
            if (yardDto.getAnimalIds() != null) {
                yard.setAnimals(yardDto.getAnimalIds().stream().map(animal -> animalRepository.findById(animal)
                        .get()).collect(Collectors.toList())); // TODO: Exception possibility NoSuchElementException
            }
            if (yardDto.getNoteIds() != null) {
                yard.setNotes(yardDto.getNoteIds().stream().map(note -> noteRepository.findById(note)
                        .get()).collect(Collectors.toList())); // TODO: Exception possibility NoSuchElementException
            }
            yard.setUser(userRepository.findUserByEmail(yardDto.getUserEmail())); // TODO: Exception possibility null
            return yard;
        }
    }
}
