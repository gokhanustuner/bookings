package com.hostfully.bookings.application.controller;

import com.hostfully.bookings.domain.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlockController {
    private final BlockService blockService;

    @Autowired
    public BlockController(final BlockService blockService) {
        this.blockService = blockService;
    }
}
