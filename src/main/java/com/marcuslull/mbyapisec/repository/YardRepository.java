package com.marcuslull.mbyapisec.repository;

import com.marcuslull.mbyapisec.model.entity.Yard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface YardRepository extends CrudRepository<Yard, Long> {
    @PreAuthorize("#email == authentication.name")
    public List<Yard> findYardsByUserEmail(String email);
}