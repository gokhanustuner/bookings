package com.hostfully.bookings.infrastructure.persistence.repository;

import com.hostfully.bookings.domain.entity.block.BlockReason;
import com.hostfully.bookings.domain.exception.BlockReasonNotFoundException;
import com.hostfully.bookings.domain.repository.BlockReasonRepository;
import com.hostfully.bookings.domain.value.BlockReasonId;
import com.hostfully.bookings.infrastructure.mapper.EntityMapper;
import com.hostfully.bookings.infrastructure.persistence.entity.BlockReasonEntity;
import com.hostfully.bookings.infrastructure.persistence.jpa.BlockReasonJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class BlockReasonRepositoryImpl implements BlockReasonRepository {

    private final BlockReasonJPARepository blockReasonJPARepository;

    private final EntityMapper<BlockReasonEntity, BlockReason> blockReasonEntityMapper;

    @Autowired
    public BlockReasonRepositoryImpl(
            final BlockReasonJPARepository blockReasonJPARepository,
            final EntityMapper<BlockReasonEntity, BlockReason> blockReasonEntityMapper
    ) {
        this.blockReasonJPARepository = blockReasonJPARepository;
        this.blockReasonEntityMapper = blockReasonEntityMapper;
    }

    @Override
    public BlockReason findById(final BlockReasonId blockReasonId) {
        return blockReasonEntityMapper
                .JPAEntityToDomainEntity(
                    blockReasonJPARepository
                            .findById(blockReasonId.value())
                            .orElseThrow(
                                    () -> new BlockReasonNotFoundException(
                                            String.format("BlockReason with id %s not found", blockReasonId)
                                    )
                            )
                );
    }
}
