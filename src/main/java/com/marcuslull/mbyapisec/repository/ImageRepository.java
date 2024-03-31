package com.marcuslull.mbyapisec.repository;

import com.marcuslull.mbyapisec.model.entity.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ImageRepository extends CrudRepository<Image, Long> {
    List<Image> findImagesByOwnerIdAndYardId(Long ownerId, Long yardId);

    @Query("select count(i) from Image i where i.ownerId = ?1")
    long countByOwnerId(@NonNull Long ownerId);
}
