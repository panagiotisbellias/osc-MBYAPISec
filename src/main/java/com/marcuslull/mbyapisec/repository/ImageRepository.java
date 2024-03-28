package com.marcuslull.mbyapisec.repository;

import com.marcuslull.mbyapisec.model.entity.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ImageRepository extends CrudRepository<Image, Long> {
    @PreAuthorize("#ownerId == userRepository.findUserByEmail(authentication.name).id")
    List<Image> findImagesByOwnerIdAndYardId(Long ownerId, Long yardId);
}
