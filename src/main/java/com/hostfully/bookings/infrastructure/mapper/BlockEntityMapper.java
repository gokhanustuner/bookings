package com.hostfully.bookings.infrastructure.mapper;

import com.hostfully.bookings.domain.entity.block.Block;
import com.hostfully.bookings.domain.entity.block.BlockReason;
import com.hostfully.bookings.domain.entity.block.BlockStatus;
import com.hostfully.bookings.domain.entity.property.Property;
import com.hostfully.bookings.domain.value.BlockId;
import com.hostfully.bookings.domain.value.BlockPeriod;
import com.hostfully.bookings.infrastructure.persistence.entity.BlockEntity;
import com.hostfully.bookings.infrastructure.persistence.entity.BlockReasonEntity;
import com.hostfully.bookings.infrastructure.persistence.entity.PropertyEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BlockEntityMapper implements EntityMapper<BlockEntity, Block> {
    @Override
    public Block JPAEntityToDomainEntity(final BlockEntity JPABlockEntity) {
        return Block.of(
                BlockId.of(JPABlockEntity.getId()),
                BlockPeriod.of(JPABlockEntity.getStartDate(), JPABlockEntity.getEndDate()),
                Property.of(JPABlockEntity.getProperty().getId(), JPABlockEntity.getProperty().getName()),
                BlockReason.of(JPABlockEntity.getBlockReason().getId(), JPABlockEntity.getBlockReason().getDescription()),
                BlockStatus.valueOf(JPABlockEntity.getBlockStatus().name())
        );
    }

    @Override
    public List<Block> JPAEntityToDomainEntity(final List<BlockEntity> JPABlockEntities) {
        return JPABlockEntities.stream().map(this::JPAEntityToDomainEntity).toList();
    }

    @Override
    public BlockEntity domainEntityToJPAEntity(final Block domainBlockEntity) {
        final BlockEntity blockEntity = new BlockEntity();
        final PropertyEntity propertyEntity = new PropertyEntity();
        final BlockReasonEntity blockReasonEntity = new BlockReasonEntity();

        if (domainBlockEntity.getBlockId() != null)
            blockEntity.setId(domainBlockEntity.getBlockId().value());

        blockEntity.setStartDate(domainBlockEntity.getBlockPeriod().startDate());
        blockEntity.setEndDate(domainBlockEntity.getBlockPeriod().endDate());
        blockEntity.setBlockStatus(domainBlockEntity.getBlockStatus());

        blockReasonEntity.setId(domainBlockEntity.getBlockReasonId().value());
        blockEntity.setBlockReason(blockReasonEntity);

        propertyEntity.setId(domainBlockEntity.getPropertyId().value());
        blockEntity.setProperty(propertyEntity);

        return blockEntity;
    }
}
