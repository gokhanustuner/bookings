package com.hostfully.bookings.infrastructure.persistence.repository;

import com.hostfully.bookings.domain.entity.Block;
import com.hostfully.bookings.domain.repository.BlockRepository;
import com.hostfully.bookings.infrastructure.persistence.jpa.BlockJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class BlockRepositoryImpl implements BlockRepository {

    private final BlockJPARepository blockJPARepository;

    @Autowired
    public BlockRepositoryImpl(final BlockJPARepository blockJPARepository) {
        this.blockJPARepository = blockJPARepository;
    }

    @Override
    public void save(Block block) {

    }
}
