package com.marcuslull.mbyapisec.repository;


import com.marcuslull.mbyapisec.model.entity.Animal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface AnimalRepository extends CrudRepository<Animal, Long> {
    @PreAuthorize("#email == authentication.name")
    public List<Animal> findAnimalsByOwner(String email);

    @PreAuthorize("#email == authentication.name")
    public Animal findAnimalByIdAndOwner(Long id, String email);

    @PreAuthorize("#email == authentication.name")
    public void deleteAnimalByIdAndOwner(Long id, String email);
}