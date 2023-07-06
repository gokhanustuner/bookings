package com.hostfully.bookings.infrastructure.persistence.entity;

import com.hostfully.bookings.domain.entity.block.BlockStatus;
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

    @Enumerated(EnumType.STRING)
    private BlockStatus blockStatus;

    @ManyToOne
    @JoinColumn(name = "block_reason_id", nullable = false)
    private BlockReasonEntity blockReason;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
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

    public BlockReasonEntity getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(BlockReasonEntity blockReason) {
        this.blockReason = blockReason;
    }

    public PropertyEntity getProperty() {
        return property;
    }

    public void setProperty(PropertyEntity property) {
        this.property = property;
    }

    public BlockStatus getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(final BlockStatus blockStatus) {
        this.blockStatus = blockStatus;
    }
}
