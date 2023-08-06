package se.mohosman.blockgen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.mohosman.blockgen.hash.Hasher;
import se.mohosman.blockgen.kafka.MessageProducer;
import se.mohosman.blockgen.model.Block;
import se.mohosman.blockgen.model.Blockchain;
import se.mohosman.blockgen.service.BlockService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;

@Service
public class BlockServiceImplementation implements BlockService {
// TODO Add logging
    private Blockchain blockchain;

    @Autowired
    private MessageProducer producer;

    public BlockServiceImplementation() {
        this.blockchain = new Blockchain(new LinkedList<>());
    }

    @Override
    public byte[] createBlock(String data) {
        if (!this.blockchain.blocks().isEmpty()) {
            var lastIndex = this.blockchain.blocks().getLast().getIndex();
            var previousHash = this.blockchain.blocks().get(lastIndex).getHash();
            Block block = new Block(lastIndex + 1,
                    previousHash,
                    Timestamp.from(Instant.now()),
                    data.getBytes());
            try {
                var hash = Hasher.createHash(block);
                block.setHash(hash);
               producer.sendMessage("Block", block);
                return hash;

            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

        } else {
            return createGenesisBLock(data);
        }
    }

    @Override
    public byte[] createGenesisBLock(String data) {
        Block block = new Block(0, new byte[]{0},
                Timestamp.from(Instant.now())
                , data.getBytes());
        try {
            byte[] hash = Hasher.createHash(block);
            block.setHash(hash);
            producer.sendMessage("Block", block);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return block.getHash();
    }

    @Override
    public Flux<Block> getBlockChain() {
        return Flux.fromIterable(this.blockchain.blocks());
    }

    @Override
    public Mono<Block> getBlock(byte[] hash) {
        return Mono.justOrEmpty(blockchain.blocks().stream()
                .filter(b -> MessageDigest.isEqual(b.getHash(), hash))
                .findFirst());
    }
    @Override
    public void addBlock(Block block) {
     this.blockchain.blocks().add(block);
    }


}



