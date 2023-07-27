package se.mohosman.blockgen.service.impl;

import com.google.protobuf.ByteString;
import io.grpc.Grpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.mohosman.blockgen.grpc.BlockchainServiceGrpc;
import se.mohosman.blockgen.grpc.GrpcBlock;
import se.mohosman.blockgen.hash.Hasher;
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

    @GrpcClient("block-sender")
    private BlockchainServiceGrpc.BlockchainServiceBlockingStub blockingStub;

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
                grpcClientSend(block);
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
        grpcClientSend(block);
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

    public void grpcClientSend(Block block) {
        blockingStub.send(toGrpcBlock(block));
    }
    @Override
    public void addBlock(Block block) {
     this.blockchain.blocks().add(block);
    }

    private GrpcBlock toGrpcBlock(Block pojoBlock) {
        return  GrpcBlock.newBuilder()
                .setIndex(pojoBlock.getIndex())
                .setHash(ByteString.copyFrom(pojoBlock.getHash()))
                .setPreviousHash(ByteString.copyFrom(pojoBlock.getPreviousHash()))
                .setTimestamp(pojoBlock.getTimestamp().getTime())
                .setData(ByteString.copyFrom(pojoBlock.getData()))
                .build();
    }
}



