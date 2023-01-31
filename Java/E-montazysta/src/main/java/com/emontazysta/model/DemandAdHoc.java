package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DemandAdHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String description;
    private String comments;
    private LocalDateTime creationTime;
    private LocalDateTime readByWarehousemanTime;
    private LocalDateTime realisationTime;
    private String warehousemanComment; // TODO: should be in other model if we want to have info about warehouseman + timestamp
    private String specialistComment; // TODO: should be in other model if we want to have info about specialist + timestamp
    // TODO: status values not defined


//    @ManyToOne
//    @JsonBackReference
//    private AppUser manager;

    @OneToMany(mappedBy = "demandAdHoc")
    private List<ToolRelease> toolReleases;

    @OneToMany(mappedBy = "demandAdHoc")
    private List<ElementReturnRelease> elementReturnReleases;

    @ManyToOne
    private WarehouseManager warehouseManager;

    @ManyToOne
    private Warehouseman warehouseman;

    @ManyToOne
    private Specialist specialist;

    @ManyToOne
    private Manager manager;

    @ManyToOne
    private Foreman foreman;

    //TODO: relationship to OrderStage
}
