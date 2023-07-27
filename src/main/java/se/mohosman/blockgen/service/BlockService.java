package se.mohosman.blockgen.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.mohosman.blockgen.model.Block;

import java.util.LinkedList;

public interface BlockService {

    byte[] createBlock(String data);
    byte[] createGenesisBLock(String data);

    Flux<Block> getBlockChain();

    Mono<Block> getBlock(byte[] hash);

    void addBlock(Block block);

}
