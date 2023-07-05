package com.hostfully.bookings.domain.command.block;

import com.hostfully.bookings.domain.value.BlockId;

public record GetBlockCommand(BlockId blockId) {
    public static GetBlockCommand of(final String blockId) {
        return new GetBlockCommand(BlockId.of(blockId));
    }
}
