package com.hostfully.bookings.domain.service;

import com.hostfully.bookings.domain.command.block.CancelBlockCommand;
import com.hostfully.bookings.domain.command.block.CreateBlockCommand;
import com.hostfully.bookings.domain.command.block.GetBlockCommand;
import com.hostfully.bookings.domain.command.block.UpdateBlockCommand;
import com.hostfully.bookings.domain.entity.block.Block;

import java.util.List;

public interface BlockService {
    Block createBlock(CreateBlockCommand createBlockCommand);

    Block updateBlock(UpdateBlockCommand updateBlockCommand);

    Block getBlock(GetBlockCommand getBlockCommand);

    List<Block> listBlocks();

    Block cancelBlock(CancelBlockCommand cancelBlockCommand);
}
