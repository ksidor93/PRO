package com.emontazysta.model;

import com.emontazysta.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE order_stage SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderStage {

    public OrderStage(Long id, String name, OrderStatus status, BigDecimal price, Integer order, LocalDate plannedEndDate,
                      LocalDateTime startDate, LocalDateTime endDate, long plannedDurationTime, int plannedFittersNumber,
                      int minimumImagesNumber, List<Fitter> assignedTo, Foreman managedBy, List<Comment> comments,
                      List<ToolRelease> toolReleases, List<ElementReturnRelease> elementReturnReleases, Orders orders,
                      List<Attachment> attachments, List<Notification> notifications, List<ToolType> tools,
                      List<Element> elements, List<DemandAdHoc> demandsAdHoc) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.price = price;
        this.order = order;
        this.plannedEndDate = plannedEndDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.plannedDurationTime = plannedDurationTime;
        this.plannedFittersNumber = plannedFittersNumber;
        this.minimumImagesNumber = minimumImagesNumber;
        this.assignedTo = assignedTo;
        this.managedBy = managedBy;
        this.comments = comments;
        this.toolReleases = toolReleases;
        this.elementReturnReleases = elementReturnReleases;
        this.orders = orders;
        this.attachments = attachments;
        this.notifications = notifications;
        this.tools = tools;
        this.elements = elements;
        this.demandsAdHoc = demandsAdHoc;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private OrderStatus status;

    private BigDecimal price;

    private LocalDateTime plannedStartDate;

    private LocalDateTime plannedEndDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private long plannedDurationTime;

    private int plannedFittersNumber;

    private int minimumImagesNumber;

    private boolean deleted = Boolean.FALSE;

    @ManyToMany(mappedBy = "workingOn")
    private List<Fitter> assignedTo;

    @ManyToOne
    private Foreman managedBy;

    @OneToMany(mappedBy = "orderStage")
    private List<Comment> comments;

    @OneToMany(mappedBy = "orderStage")
    private List<ToolRelease> toolReleases;

    @OneToMany(mappedBy = "orderStage")
    private List<ElementReturnRelease> elementReturnReleases;

    @ManyToOne
    private Orders orders;

    @OneToMany(mappedBy = "orderStage")
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "orderStage")
    private List<Notification> notifications;

    @ManyToMany
    private List<ToolType> tools;

    @ManyToMany
    private List<Element> elements;

    @ManyToMany
    private List<DemandAdHoc> demandsAdHoc;



    //TODO: relationship to Element, ToolType, Fitter, DemandAdHoc  needed (many to many) should be replaced with association table in diagram
}

