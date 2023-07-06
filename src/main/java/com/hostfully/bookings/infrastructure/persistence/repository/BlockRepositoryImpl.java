package com.hostfully.bookings.infrastructure.persistence.repository;

import com.hostfully.bookings.domain.entity.block.Block;
import com.hostfully.bookings.domain.entity.block.BlockStatus;
import com.hostfully.bookings.domain.entity.property.Property;
import com.hostfully.bookings.domain.exception.BlockNotFoundException;
import com.hostfully.bookings.domain.repository.BlockRepository;
import com.hostfully.bookings.domain.value.BlockId;
import com.hostfully.bookings.domain.value.BlockPeriod;
import com.hostfully.bookings.infrastructure.mapper.EntityMapper;
import com.hostfully.bookings.infrastructure.persistence.entity.BlockEntity;
import com.hostfully.bookings.infrastructure.persistence.entity.PropertyEntity;
import com.hostfully.bookings.infrastructure.persistence.jpa.BlockJPARepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class BlockRepositoryImpl implements BlockRepository {

    private final BlockJPARepository blockJPARepository;

    private final EntityMapper<BlockEntity, Block> blockEntityMapper;

    private final EntityMapper<PropertyEntity, Property> propertyEntityMapper;

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public BlockRepositoryImpl(
            final BlockJPARepository blockJPARepository,
            final EntityMapper<BlockEntity, Block> blockEntityMapper,
            final EntityMapper<PropertyEntity, Property> propertyEntityMapper,
            final EntityManagerFactory entityManagerFactory
    ) {
        this.blockJPARepository = blockJPARepository;
        this.blockEntityMapper = blockEntityMapper;
        this.propertyEntityMapper = propertyEntityMapper;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Block save(final Block block) {
        return blockEntityMapper.JPAEntityToDomainEntity(
                blockJPARepository.save(
                        blockEntityMapper.domainEntityToJPAEntity(block)
                )
        );
    }

    @Override
    public Block findById(final BlockId blockId) {
        final BlockEntity blockEntity = blockJPARepository.findById(blockId.value()).orElseThrow(
                () -> new BlockNotFoundException(
                        String.format("Block with id %s not found", blockId)
                )
        );

        return blockEntityMapper.JPAEntityToDomainEntity(blockEntity);
    }

    @Override
    public List<Block> findAll() {
        return blockEntityMapper.JPAEntityToDomainEntity(
                (List<BlockEntity>) blockJPARepository.findAll()
        );
    }

    @Override
    public List<Block> findActiveBlocksByPropertyAndBlockPeriod(final Property property, final BlockPeriod blockPeriod) {
        return blockEntityMapper.JPAEntityToDomainEntity(
                blockJPARepository.findBlockEntitiesByPropertyEqualsAndStartDateIsLessThanEqualAndEndDateIsGreaterThanAndBlockStatusEquals(
                        propertyEntityMapper.domainEntityToJPAEntity(property),
                        blockPeriod.endDate(),
                        blockPeriod.startDate(),
                        BlockStatus.ACTIVE
                )
        );
    }

    @Override
    public Block findActiveBlockById(final BlockId blockId) {
        final BlockEntity blockEntity = blockJPARepository.findBlockEntitiesByIdIsAndBlockStatusEquals(
                blockId.value(),
                BlockStatus.ACTIVE
        );

        if (blockEntity == null)
            throw new BlockNotFoundException(String.format("Block with id %s not found", blockId));

        return blockEntityMapper.JPAEntityToDomainEntity(blockEntity);
    }
}
