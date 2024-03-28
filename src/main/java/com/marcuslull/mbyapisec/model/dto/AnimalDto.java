package com.marcuslull.mbyapisec.model.dto;

import com.marcuslull.mbyapisec.model.enums.AnimalSubType;
import com.marcuslull.mbyapisec.model.enums.DietType;
import com.marcuslull.mbyapisec.model.enums.NativeAreaType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.marcuslull.mbyapisec.model.entity.Animal}
 */
@Getter
@Setter
@ToString
public class AnimalDto implements Serializable {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String owner;
    private String name;
    private AnimalSubType animalSubType;
    private DietType dietType;
    private NativeAreaType nativeAreaType;
    private Long yardId;
}