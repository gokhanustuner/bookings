package com.hostfully.bookings.domain.value;

import com.hostfully.bookings.domain.exception.InvalidEntityIdException;

public record BlockId(Long value) {
    public static BlockId of(final String blockId) {
        try {
            if (blockId == null)
                throw new InvalidEntityIdException("blockId must be provided");

            if (Long.parseLong(blockId) < 1)
                throw new InvalidEntityIdException("blockId must be a positive number");

            return new BlockId(Long.parseLong(blockId));
        } catch (final NumberFormatException e) {
            throw new InvalidEntityIdException("blockId must be a numeric entity", e);
        }
    }

    public static BlockId of(final Long blockId) {
        if (blockId == null)
            throw new InvalidEntityIdException("blockId must be provided");

        if (blockId < 1)
            throw new InvalidEntityIdException("blockId must be a positive number");

        return new BlockId(blockId);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
