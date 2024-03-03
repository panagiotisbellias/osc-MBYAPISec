package com.marcuslull.mbyapisec.model.entity;

import com.marcuslull.mbyapisec.model.enums.AnimalSubType;
import com.marcuslull.mbyapisec.model.enums.DietType;
import com.marcuslull.mbyapisec.model.enums.NativeAreaType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "animal")
public class Animal {
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

    @Column(name = "owner", nullable = false)
    private String owner;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_sub_type", nullable = false)
    private AnimalSubType animalSubType;

    @Enumerated(EnumType.STRING)
    @Column(name = "diet_type", nullable = false)
    private DietType dietType;

    @Enumerated(EnumType.STRING)
    @Column(name = "native_area_type", nullable = false)
    private NativeAreaType nativeAreaType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yard_id")
    private Yard yard;

}