package com.marcuslull.mbyapisec.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.marcuslull.mbyapisec.model.entity.Image}
 */
@Getter
@Setter
public class ImageDto implements Serializable {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Long ownerId;
    private String fileName;
    private Long fileSize;
    private String path;
    private Long yardId;
}