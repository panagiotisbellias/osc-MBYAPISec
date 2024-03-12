package com.marcuslull.mbyapisec.repository;

import com.marcuslull.mbyapisec.model.entity.Plant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface PlantRepository extends CrudRepository<Plant, Long> {
    @PreAuthorize("#email == authentication.name")
    List<Plant> findPlantsByOwner(String email);

    @PreAuthorize("#email == authentication.name")
    Plant findPlantByIdAndOwner(Long id, String email);

    @PreAuthorize("#email == authentication.name")
    void deletePlantByIdAndOwner(Long id, String email);
}