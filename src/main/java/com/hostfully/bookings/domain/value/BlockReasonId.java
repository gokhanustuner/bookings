package com.hostfully.bookings.domain.value;

import com.hostfully.bookings.domain.exception.InvalidEntityIdException;

public record BlockReasonId(Long value) {
    public static BlockReasonId of(final String blockReasonId) {
        try {
            if (blockReasonId == null)
                throw new InvalidEntityIdException("blockReasonId must be provided");

            if (Long.parseLong(blockReasonId) < 1)
                throw new InvalidEntityIdException("blockReasonId must be a positive number");

            return new BlockReasonId(Long.parseLong(blockReasonId));
        } catch (final NumberFormatException e) {
            throw new InvalidEntityIdException("blockReasonId must be a numeric entity", e);
        }
    }

    public static BlockReasonId of(final Long blockReasonId) {
        if (blockReasonId == null)
            throw new InvalidEntityIdException("blockReasonId must be provided");

        if (blockReasonId < 1)
            throw new InvalidEntityIdException("blockReasonId must be a positive number");

        return new BlockReasonId(blockReasonId);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
