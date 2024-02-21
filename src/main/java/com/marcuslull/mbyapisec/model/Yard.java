package com.marcuslull.mbyapisec.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.marcuslull.mbyapisec.model.enums.HardinessZone;
import com.marcuslull.mbyapisec.model.enums.YardSubType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // prevents infinite recursion
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "yard")
public class Yard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "created", nullable = false)
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name = "updated")
    @UpdateTimestamp
    private LocalDateTime updated;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "hardiness_zone", nullable = false)
    private HardinessZone hardinessZone;

    @Enumerated(EnumType.STRING)
    @Column(name = "yard_sub_type", nullable = false)
    private YardSubType yardSubType;

    @OneToMany(mappedBy = "yard", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Plant> plants = new ArrayList<>();

    @OneToMany(mappedBy = "yard", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Animal> animals = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}