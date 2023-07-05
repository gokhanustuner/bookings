package com.hostfully.bookings.domain.command.block;

import com.hostfully.bookings.domain.entity.block.BlockStatus;
import com.hostfully.bookings.domain.value.BlockId;

public record CancelBlockCommand(BlockId blockId, BlockStatus blockStatus) {
    public static CancelBlockCommand of(final String blockId) {
        return new CancelBlockCommand(BlockId.of(blockId), BlockStatus.CANCELLED);
    }
}
