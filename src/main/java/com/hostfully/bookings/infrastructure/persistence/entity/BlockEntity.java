package com.hostfully.bookings.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "block")
public class BlockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startDate;

    private Date endDate;

    private String name;

    @ManyToOne
    @JoinColumn(name = "block_type_id")
    private BlockTypeEntity blockType;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BlockTypeEntity getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockTypeEntity blockType) {
        this.blockType = blockType;
    }

    public PropertyEntity getProperty() {
        return property;
    }

    public void setProperty(PropertyEntity property) {
        this.property = property;
    }
}
