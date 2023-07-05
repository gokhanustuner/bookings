package com.hostfully.bookings.domain.service;

import com.hostfully.bookings.domain.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class BlockServiceImpl implements BlockService {
    private final BlockRepository blockRepository;

    @Autowired
    public BlockServiceImpl(final BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }
    @Override
    public void createBlock() {

    }

    @Override
    public void updateBlock() {

    }

    @Override
    public void deleteBlock() {

    }
}
