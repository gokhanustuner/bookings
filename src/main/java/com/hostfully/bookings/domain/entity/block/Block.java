package com.hostfully.bookings.domain.entity.block;

import com.hostfully.bookings.domain.entity.property.Property;
import com.hostfully.bookings.domain.value.*;

public class Block {
    private BlockId blockId;

    private BlockPeriod blockPeriod;

    private Property property;

    private BlockReason blockReason;

    private BlockStatus blockStatus;

    private Block(
            final BlockId blockId,
            final BlockPeriod blockPeriod,
            final Property property,
            final BlockReason blockReason,
            final BlockStatus blockStatus
    ) {
        this.blockId = blockId;
        this.blockPeriod = blockPeriod;
        this.property = property;
        this.blockReason = blockReason;
        this.blockStatus = blockStatus;
    }

    public static Block of(
            final BlockPeriod blockPeriod,
            final Property property,
            final BlockReason blockReason,
            final BlockStatus blockStatus
    ) {
        return new Block(null, blockPeriod, property, blockReason, blockStatus);
    }
    public static Block of(
            final BlockId blockId,
            final BlockPeriod blockPeriod,
            final Property property,
            final BlockReason blockReason,
            final BlockStatus blockStatus
    ) {
        return new Block(blockId, blockPeriod, property, blockReason, blockStatus);
    }

    public Boolean active() {
        return blockStatus == BlockStatus.ACTIVE;
    }

    public BlockId getBlockId() {
        return blockId;
    }

    public void setBlockId(final BlockId blockId) {
        this.blockId = blockId;
    }

    public BlockPeriod getBlockPeriod() {
        return blockPeriod;
    }

    public void setBlockPeriod(final BlockPeriod blockPeriod) {
        this.blockPeriod = blockPeriod;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(final Property property) {
        this.property = property;
    }

    public BlockReason getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(final BlockReason blockReason) {
        this.blockReason = blockReason;
    }

    public PropertyId getPropertyId() {
        return property.getPropertyId();
    }

    public PropertyName getPropertyName() {
        return property.getPropertyName();
    }

    public BlockReasonId getBlockReasonId() {
        return blockReason.getBlockReasonId();
    }

    public BlockReasonDescription getBlockReasonDescription() {
        return blockReason.getBlockReasonDescription();
    }

    public BlockStatus getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(final BlockStatus blockStatus) {
        this.blockStatus = blockStatus;
    }
}
