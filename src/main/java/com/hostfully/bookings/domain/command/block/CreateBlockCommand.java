package com.hostfully.bookings.domain.command.block;

import com.hostfully.bookings.domain.entity.block.BlockStatus;
import com.hostfully.bookings.domain.value.BlockPeriod;
import com.hostfully.bookings.domain.value.BlockReasonId;
import com.hostfully.bookings.domain.value.PropertyId;

public record CreateBlockCommand(
        BlockPeriod blockPeriod,
        PropertyId propertyId,
        BlockReasonId blockReasonId,
        BlockStatus blockStatus
) {
    public static CreateBlockCommand of(
            final String startDate,
            final String endDate,
            final String propertyId,
            final String blockReasonId
    ) {
        return new CreateBlockCommand(
                BlockPeriod.of(startDate, endDate),
                PropertyId.of(propertyId),
                BlockReasonId.of(blockReasonId),
                BlockStatus.ACTIVE
        );
    }
}
