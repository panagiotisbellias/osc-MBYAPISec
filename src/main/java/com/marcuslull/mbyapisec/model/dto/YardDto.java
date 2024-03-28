package com.marcuslull.mbyapisec.model.dto;

import com.marcuslull.mbyapisec.model.enums.HardinessZone;
import com.marcuslull.mbyapisec.model.enums.YardSubType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.marcuslull.mbyapisec.model.entity.Yard}
 */
@Getter
@Setter
@ToString
public class YardDto implements Serializable {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String name;
    private HardinessZone hardinessZone;
    private YardSubType yardSubType;
    private List<Long> plantIds;
    private List<Long> animalIds;
    private List<Long> noteIds;
    private String userEmail;
}