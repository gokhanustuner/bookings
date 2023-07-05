package com.hostfully.bookings.domain.service;

import com.hostfully.bookings.domain.command.block.CancelBlockCommand;
import com.hostfully.bookings.domain.command.block.CreateBlockCommand;
import com.hostfully.bookings.domain.command.block.GetBlockCommand;
import com.hostfully.bookings.domain.command.block.UpdateBlockCommand;
import com.hostfully.bookings.domain.entity.block.Block;
import com.hostfully.bookings.domain.entity.block.BlockReason;
import com.hostfully.bookings.domain.entity.booking.Booking;
import com.hostfully.bookings.domain.entity.booking.BookingStatus;
import com.hostfully.bookings.domain.entity.property.Property;
import com.hostfully.bookings.domain.repository.BlockReasonRepository;
import com.hostfully.bookings.domain.repository.BlockRepository;
import com.hostfully.bookings.domain.repository.BookingRepository;
import com.hostfully.bookings.domain.repository.PropertyRepository;
import com.hostfully.bookings.domain.value.BlockPeriod;
import com.hostfully.bookings.domain.value.BookingPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class BlockServiceImpl implements BlockService {
    private final BlockRepository blockRepository;

    private final PropertyRepository propertyRepository;

    private final BookingRepository bookingRepository;

    private final BlockReasonRepository blockReasonRepository;

    @Autowired
    public BlockServiceImpl(
            final BlockRepository blockRepository,
            final PropertyRepository propertyRepository,
            final BookingRepository bookingRepository,
            final BlockReasonRepository blockReasonRepository
    ) {
        this.blockRepository = blockRepository;
        this.propertyRepository = propertyRepository;
        this.bookingRepository = bookingRepository;
        this.blockReasonRepository = blockReasonRepository;
    }

    @Override
    public Block createBlock(final CreateBlockCommand createBlockCommand) {
        final Property property = propertyRepository.findById(createBlockCommand.propertyId());
        final BlockReason blockReason = blockReasonRepository.findById(createBlockCommand.blockReasonId());
        final List<Booking> bookingsOverlap = getBookingsOverlapInBlockPeriod(property, createBlockCommand.blockPeriod());

        bookingsOverlap.forEach((booking) -> booking.setBookingStatus(BookingStatus.CANCELLED));
        bookingRepository.saveAll(bookingsOverlap);

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
        final Block block = blockRepository.findActiveBlockById(updateBlockCommand.blockId());

        final List<Booking> bookingsOverlap = getBookingsOverlapInBlockPeriod(block.getProperty(), updateBlockCommand.blockPeriod());
        bookingsOverlap.forEach((booking) -> booking.setBookingStatus(BookingStatus.CANCELLED));
        bookingRepository.saveAll(bookingsOverlap);

        block.setBlockPeriod(updateBlockCommand.blockPeriod());
        block.setBlockReason(BlockReason.of(updateBlockCommand.blockReasonId()));

        blockRepository.save(block);

        return blockRepository.findById(block.getBlockId());
    }

    @Override
    public Block cancelBlock(final CancelBlockCommand cancelBlockCommand) {
        final Block block = blockRepository.findActiveBlockById(cancelBlockCommand.blockId());

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

    private List<Booking> getBookingsOverlapInBlockPeriod(final Property property, final BlockPeriod blockPeriod) {
        return bookingRepository.findActiveBookingsByPropertyAndBookingPeriod(
                property,
                BookingPeriod.of(blockPeriod.startDate(), blockPeriod.endDate())
        );
    }
}
