package com.hostfully.bookings.application.response;

import com.hostfully.bookings.domain.entity.block.Block;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public record BlockResponse(
        Long id,
        String startDate,
        String endDate,
        PropertyNode property,
        BlockReasonNode reason,
        String status
) {
    public static BlockResponse of(
            final Long blockId,
            final Long propertyId,
            final String propertyName,
            final String startDate,
            final String endDate,
            final Long blockReasonId,
            final String blockReasonDescription,
            final String blockStatus
    ) {
        return new BlockResponse(
                blockId,
                startDate,
                endDate,
                new PropertyNode(propertyId, propertyName),
                new BlockReasonNode(blockReasonId, blockReasonDescription),
                blockStatus
        );
    }

    public static BlockResponse of(final Block block) {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return of(
                block.getBlockId().value(),
                block.getPropertyId().value(),
                block.getPropertyName().value(),
                dateFormat.format(block.getBlockPeriod().startDate()),
                dateFormat.format(block.getBlockPeriod().endDate()),
                block.getBlockReasonId().value(),
                block.getBlockReasonDescription().value(),
                block.getBlockStatus().name()
        );
    }
}
