package com.hostfully.bookings.domain.repository;

import com.hostfully.bookings.domain.entity.block.Block;
import com.hostfully.bookings.domain.entity.property.Property;
import com.hostfully.bookings.domain.value.BlockId;
import com.hostfully.bookings.domain.value.BlockPeriod;

import java.util.List;

public interface BlockRepository {
    Block save(Block block);

    Block findById(BlockId blockId);

    List<Block> findAll();

    List<Block> findActiveBlocksByPropertyAndBlockPeriod(Property property, BlockPeriod blockPeriod);
}
