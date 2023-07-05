package com.hostfully.bookings.application.controller;

import com.hostfully.bookings.application.request.block.CreateBlockRequest;
import com.hostfully.bookings.application.request.block.UpdateBlockRequest;
import com.hostfully.bookings.application.response.BlockResponse;
import com.hostfully.bookings.domain.command.block.CancelBlockCommand;
import com.hostfully.bookings.domain.command.block.CreateBlockCommand;
import com.hostfully.bookings.domain.command.block.GetBlockCommand;
import com.hostfully.bookings.domain.command.block.UpdateBlockCommand;
import com.hostfully.bookings.domain.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blocks")
public class BlockController {
    private final BlockService blockService;

    @Autowired
    public BlockController(final BlockService blockService) {
        this.blockService = blockService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BlockResponse create(@RequestBody final CreateBlockRequest createBlockRequest) {
        return BlockResponse.of(
                blockService.createBlock(
                        CreateBlockCommand.of(
                                createBlockRequest.startDate(),
                                createBlockRequest.endDate(),
                                createBlockRequest.propertyId(),
                                createBlockRequest.reasonId()
                        )
                )
        );
    }

    @GetMapping(value = "/{blockId}")
    public BlockResponse read(@PathVariable final String blockId) {
        return BlockResponse.of(
                blockService.getBlock(GetBlockCommand.of(blockId))
        );
    }

    @GetMapping
    public List<BlockResponse> list() {
        return blockService.listBlocks().stream().map(BlockResponse::of).toList();
    }

    @PutMapping(value = "/{blockId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BlockResponse update(
            @RequestBody final UpdateBlockRequest updateBlockRequest,
            @PathVariable final String blockId
    ) {
        return BlockResponse.of(
                blockService.updateBlock(
                        UpdateBlockCommand.of(
                                blockId,
                                updateBlockRequest.startDate(),
                                updateBlockRequest.endDate(),
                                updateBlockRequest.reasonId()
                        )
                )
        );
    }

    @DeleteMapping("/{blockId}")
    public BlockResponse delete(@PathVariable final String blockId) {
        return BlockResponse.of(
                blockService.cancelBlock(
                        CancelBlockCommand.of(blockId)
                )
        );
    }
}
