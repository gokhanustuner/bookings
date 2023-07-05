package com.hostfully.bookings.domain.command.block;

import com.hostfully.bookings.domain.value.BlockId;
import com.hostfully.bookings.domain.value.BlockPeriod;
import com.hostfully.bookings.domain.value.BlockReasonId;

public record UpdateBlockCommand(BlockId blockId, BlockPeriod blockPeriod, BlockReasonId blockReasonId) {
    public static UpdateBlockCommand of(
            final String blockId,
            final String startDate,
            final String endDate,
            final String blockReasonId
    ) {
        return new UpdateBlockCommand(
                BlockId.of(blockId),
                BlockPeriod.of(startDate, endDate),
                BlockReasonId.of(blockReasonId)
        );
    }
}
