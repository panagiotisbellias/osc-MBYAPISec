package com.marcuslull.mbyapisec.model.dto;

import com.marcuslull.mbyapisec.model.enums.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.marcuslull.mbyapisec.model.entity.Plant}
 */
@Getter
@Setter
@ToString
public class PlantDto implements Serializable {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String owner;
    private String name;
    private HardinessZone hardinessZone;
    private NativeAreaType nativeAreaType;
    private PlantSubType plantSubType;
    private SoilType soilType;
    private SunExposure sunExposure;
    private WateringFrequency wateringFrequency;
    private Long yardId;
}