package se.mohosman.blockgen.service;

import reactor.core.publisher.Mono;
import se.mohosman.blockgen.model.Block;

import java.lang.management.MonitorInfo;
import java.util.Optional;

public interface BlockService {

    byte[] createBlock(String data);
    byte[] createGenesisBLock(String data);

    Mono<Block> getBlock(byte[] hash);
}
