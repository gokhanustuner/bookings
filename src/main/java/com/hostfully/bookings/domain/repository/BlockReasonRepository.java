package com.hostfully.bookings.domain.repository;

import com.hostfully.bookings.domain.entity.block.BlockReason;
import com.hostfully.bookings.domain.value.BlockReasonId;

public interface BlockReasonRepository {
    BlockReason findById(BlockReasonId blockReasonId);
}
