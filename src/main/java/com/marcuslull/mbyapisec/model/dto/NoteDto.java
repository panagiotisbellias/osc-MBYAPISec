package com.marcuslull.mbyapisec.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class NoteDto implements Serializable {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String owner;
    private String comment;
    private Long yardId;
}