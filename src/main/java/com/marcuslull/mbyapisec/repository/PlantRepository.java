package com.marcuslull.mbyapisec.repository;

import com.marcuslull.mbyapisec.model.Plant;
import org.springframework.data.repository.CrudRepository;

public interface PlantRepository extends CrudRepository<Plant, Long> {
}