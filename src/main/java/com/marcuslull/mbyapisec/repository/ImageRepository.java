package com.marcuslull.mbyapisec.repository;

import com.marcuslull.mbyapisec.model.entity.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImageRepository extends CrudRepository<Image, Long> {
    List<Image> findImagesByOwnerIdAndYardId(Long ownerId, Long yardId);
}
