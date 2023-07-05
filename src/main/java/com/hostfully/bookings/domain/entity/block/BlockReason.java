package com.hostfully.bookings.domain.entity.block;

import com.hostfully.bookings.domain.value.*;

public class BlockReason {
    private BlockReasonId blockReasonId;

    private BlockReasonDescription blockReasonDescription;

    private BlockReason(final BlockReasonId blockReasonId, final BlockReasonDescription blockReasonDescription) {
        this.blockReasonId = blockReasonId;
        this.blockReasonDescription = blockReasonDescription;
    }
    public static BlockReason of(final Long blockReasonId, final String blockReasonDescription) {
        return new BlockReason(
                BlockReasonId.of(blockReasonId),
                new BlockReasonDescription(blockReasonDescription)
        );
    }

    public static BlockReason of(final BlockReasonId blockReasonId) {
        return new BlockReason(blockReasonId, null);
    }

    public static BlockReason of(
            final BlockReasonId blockReasonId,
            final BlockReasonDescription blockReasonDescription
    ) {
        return new BlockReason(blockReasonId,blockReasonDescription);
    }

    public BlockReasonId getBlockReasonId() {
        return blockReasonId;
    }

    public void setBlockReasonId(final BlockReasonId blockReasonId) {
        this.blockReasonId = blockReasonId;
    }

    public BlockReasonDescription getBlockReasonDescription() {
        return blockReasonDescription;
    }

    public void setBlockReasonDescription(final BlockReasonDescription blockReasonDescription) {
        this.blockReasonDescription = blockReasonDescription;
    }
}
