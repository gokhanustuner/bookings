package com.hostfully.bookings.infrastructure.mapper;

import com.hostfully.bookings.domain.entity.block.BlockReason;
import com.hostfully.bookings.infrastructure.persistence.entity.BlockReasonEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BlockReasonEntityMapper implements EntityMapper<BlockReasonEntity, BlockReason> {
    @Override
    public BlockReason JPAEntityToDomainEntity(final BlockReasonEntity JPABlockReasonEntity) {
        return BlockReason.of(JPABlockReasonEntity.getId(), JPABlockReasonEntity.getDescription());
    }

    @Override
    public List<BlockReason> JPAEntityToDomainEntity(final List<BlockReasonEntity> JPABlockReasonEntities) {
        return JPABlockReasonEntities.stream().map(this::JPAEntityToDomainEntity).toList();
    }

    @Override
    public BlockReasonEntity domainEntityToJPAEntity(final BlockReason domainBlockReasonEntity) {
        final BlockReasonEntity blockReasonEntity = new BlockReasonEntity();

        if (domainBlockReasonEntity.getBlockReasonId() != null)
            blockReasonEntity.setId(domainBlockReasonEntity.getBlockReasonId().value());

        blockReasonEntity.setDescription(domainBlockReasonEntity.getBlockReasonDescription().value());

        return blockReasonEntity;
    }
}
