package com.hostfully.bookings.infrastructure.persistence.repository;

import com.hostfully.bookings.domain.entity.block.Block;
import com.hostfully.bookings.domain.exception.BlockNotFoundException;
import com.hostfully.bookings.domain.repository.BlockRepository;
import com.hostfully.bookings.domain.value.BlockId;
import com.hostfully.bookings.infrastructure.mapper.EntityMapper;
import com.hostfully.bookings.infrastructure.persistence.entity.BlockEntity;
import com.hostfully.bookings.infrastructure.persistence.jpa.BlockJPARepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public final class BlockRepositoryImpl implements BlockRepository {

    private final BlockJPARepository blockJPARepository;

    private final EntityMapper<BlockEntity, Block> blockEntityMapper;

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public BlockRepositoryImpl(
            final BlockJPARepository blockJPARepository,
            final EntityMapper<BlockEntity, Block> blockEntityMapper,
            final EntityManagerFactory entityManagerFactory
    ) {
        this.blockJPARepository = blockJPARepository;
        this.blockEntityMapper = blockEntityMapper;
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
        final List<Object[]> blockQueryResultList = entityManagerFactory
                .createEntityManager()
                .createQuery(
                        "SELECT b, p, br FROM BlockEntity b, PropertyEntity p, BlockReasonEntity br " +
                                "WHERE b.property = p AND b.blockReason = br AND b.id = :blockId"
                )
                .setParameter("blockId", blockId.value())
                .getResultList();

        final BlockEntity blockEntity =
                (BlockEntity) Arrays
                        .stream(
                                blockQueryResultList
                                        .stream()
                                        .findFirst()
                                        .orElseThrow(
                                                () -> new BlockNotFoundException(
                                                        String.format("Block with id %s not found", blockId)
                                                )
                                        )
                        )
                        .findFirst()
                        .orElseThrow();

        return blockEntityMapper.JPAEntityToDomainEntity(blockEntity);
    }

    @Override
    public List<Block> findAll() {
        return blockEntityMapper.JPAEntityToDomainEntity(
                (List<BlockEntity>) blockJPARepository.findAll()
        );
    }
}
