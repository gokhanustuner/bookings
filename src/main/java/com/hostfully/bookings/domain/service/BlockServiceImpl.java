package com.hostfully.bookings.domain.service;

import com.hostfully.bookings.domain.command.block.CancelBlockCommand;
import com.hostfully.bookings.domain.command.block.CreateBlockCommand;
import com.hostfully.bookings.domain.command.block.GetBlockCommand;
import com.hostfully.bookings.domain.command.block.UpdateBlockCommand;
import com.hostfully.bookings.domain.entity.block.Block;
import com.hostfully.bookings.domain.entity.block.BlockReason;
import com.hostfully.bookings.domain.entity.property.Property;
import com.hostfully.bookings.domain.repository.BlockReasonRepository;
import com.hostfully.bookings.domain.repository.BlockRepository;
import com.hostfully.bookings.domain.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class BlockServiceImpl implements BlockService {
    private final BlockRepository blockRepository;

    private final PropertyRepository propertyRepository;

    private final BlockReasonRepository blockReasonRepository;

    @Autowired
    public BlockServiceImpl(
            final BlockRepository blockRepository,
            final PropertyRepository propertyRepository,
            final BlockReasonRepository blockReasonRepository
    ) {
        this.blockRepository = blockRepository;
        this.propertyRepository = propertyRepository;
        this.blockReasonRepository = blockReasonRepository;
    }

    @Override
    public Block createBlock(final CreateBlockCommand createBlockCommand) {
        final Property property = propertyRepository.findById(createBlockCommand.propertyId());
        final BlockReason blockReason = blockReasonRepository.findById(createBlockCommand.blockReasonId());

        final Block block = blockRepository.save(
                Block.of(
                        createBlockCommand.blockPeriod(),
                        property,
                        blockReason,
                        createBlockCommand.blockStatus()
                )
        );

        return blockRepository.findById(block.getBlockId());
    }

    @Override
    public Block updateBlock(final UpdateBlockCommand updateBlockCommand) {
        final Block block = blockRepository.findById(updateBlockCommand.blockId());

        block.setBlockPeriod(updateBlockCommand.blockPeriod());
        block.setBlockReason(BlockReason.of(updateBlockCommand.blockReasonId()));

        blockRepository.save(block);

        return blockRepository.findById(block.getBlockId());
    }

    @Override
    public Block cancelBlock(final CancelBlockCommand cancelBlockCommand) {
        final Block block = blockRepository.findById(cancelBlockCommand.blockId());

        block.setBlockStatus(cancelBlockCommand.blockStatus());
        blockRepository.save(block);

        return block;
    }

    @Override
    public List<Block> listBlocks() {
        return blockRepository.findAll();
    }

    @Override
    public Block getBlock(final GetBlockCommand getBlockCommand) {
        return blockRepository.findById(getBlockCommand.blockId());
    }
}
