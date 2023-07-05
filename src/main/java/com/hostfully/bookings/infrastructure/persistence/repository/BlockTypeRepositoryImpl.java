package com.hostfully.bookings.infrastructure.persistence.repository;

import com.hostfully.bookings.domain.entity.BlockType;
import com.hostfully.bookings.domain.repository.BlockTypeRepository;
import com.hostfully.bookings.infrastructure.persistence.jpa.BlockTypeJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class BlockTypeRepositoryImpl implements BlockTypeRepository {

    private final BlockTypeJPARepository blockTypeJPARepository;

    @Autowired
    public BlockTypeRepositoryImpl(final BlockTypeJPARepository blockTypeJPARepository) {
        this.blockTypeJPARepository = blockTypeJPARepository;
    }
    @Override
    public void save(BlockType blockType) {

    }
}
