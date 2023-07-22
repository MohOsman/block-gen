package se.mohosman.blockgen.service.impl;

import com.fasterxml.jackson.databind.type.ClassStack;
import lombok.val;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import se.mohosman.blockgen.hash.Hasher;
import se.mohosman.blockgen.model.Block;
import se.mohosman.blockgen.model.Blockchain;
import se.mohosman.blockgen.service.BlockService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

@Service
public class BlockServiceImplementation implements BlockService {

    private Blockchain blockchain;

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
                this.blockchain.blocks().add(block);
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
            this.blockchain.blocks().add(block);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return block.getHash();
    }

    @Override
    public Mono<Block> getBlock(byte[] hash) {
        return Mono.justOrEmpty(blockchain.blocks().stream()
                .filter(b -> MessageDigest.isEqual(b.getHash(), hash))
                .findFirst());
    }

}
