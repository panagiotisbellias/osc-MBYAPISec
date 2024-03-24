package com.marcuslull.mbyapisec.service;

import com.marcuslull.mbyapisec.model.dto.YardDto;
import com.marcuslull.mbyapisec.model.entity.Yard;
import com.marcuslull.mbyapisec.repository.YardRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class YardService {
    private final YardRepository yardRepository;
    private final MapperService mapperService;

    public YardService(YardRepository yardRepository, MapperService mapperService) {
        this.yardRepository = yardRepository;
        this.mapperService = mapperService;
    }

    public List<YardDto> getYards() {
        List<YardDto> yardDtos = new ArrayList<>();
        yardRepository.findYardsByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()) // TODO: Exception possibility null
                .forEach(yard -> {
                    YardDto yardDto = mapperService.map(yard);
                    yardDtos.add(yardDto);
                });
        return yardDtos;
    }

    public YardDto getYard(Long id) {
        Yard yard = yardRepository.findYardByIdAndUserEmail(id, SecurityContextHolder.getContext().getAuthentication().getName()); // TODO: Exception possibility null
        if (yard != null) {
            return mapperService.map(yard);
        }
        return null; // TODO: Exception possibility null
    }

    public YardDto postYard(YardDto yardDto) {
        yardDto.setUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()); // TODO: Exception possibility null
        return mapperService.map(yardRepository.save(mapperService.map(yardDto))); // TODO: Exception possibility IllegalArgumentException
    }

    public YardDto putYard(String id, YardDto yardDto) {
        yardDto.setId(Long.valueOf(id)); // TODO: Exception possibility NumberFormatException
        yardDto.setUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()); // TODO: Exception possibility null
        Yard yard = yardRepository.findYardByIdAndUserEmail(yardDto.getId(), yardDto.getUserEmail()); // TODO: Exception possibility null
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

            return mapperService.map(yardRepository.save(mapperService.map(yardDto))); // TODO: Exception possibility IllegalArgumentException
        }
        return null; // TODO: Exception possibility null
    }

    @Transactional
    public void deleteYard(Long id) {
        if (yardRepository.existsById(id)) { // TODO: Exception possibility IllegalArgumentException
            yardRepository.deleteYardByIdAndUserEmail(id, SecurityContextHolder.getContext().getAuthentication().getName()); // TODO: Exception possibility null
        }
    }
}
