package com.marcuslull.mbyapisec.model;

import com.marcuslull.mbyapisec.model.enums.*;
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
@Table(name = "plant")
public class Plant {
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
    @Column(name = "native_area_type", nullable = false)
    private NativeAreaType nativeAreaType;

    @Enumerated(EnumType.STRING)
    @Column(name = "plant_sub_type", nullable = false)
    private PlantSubType plantSubType;

    @Enumerated(EnumType.STRING)
    @Column(name = "soil_type", nullable = false)
    private SoilType soilType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sun_exposure", nullable = false)
    private SunExposure sunExposure;

    @Enumerated(EnumType.STRING)
    @Column(name = "watering_frequency", nullable = false)
    private WateringFrequency wateringFrequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yard_id")
    private Yard yard;

}