package se.mohosman.blockgen.service.impl;
import com.google.protobuf.ByteString;
import io.grpc.Grpc;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.mohosman.blockgen.grpc.Blockchain;
import se.mohosman.blockgen.grpc.BlockchainServiceGrpc;
import reactor.core.publisher.Flux;
import io.grpc.stub.StreamObserver;
import se.mohosman.blockgen.grpc.GrpcBlock;
import se.mohosman.blockgen.model.Block;
import se.mohosman.blockgen.service.BlockService;

import java.sql.Timestamp;

@GrpcService
public class BlockGenGRPCServiceImpl extends BlockchainServiceGrpc.BlockchainServiceImplBase {

    @Autowired
    private BlockService blockService;


    @Override
    public void send(GrpcBlock request, StreamObserver<GrpcBlock> responseObserver) {
        Block block = new Block(request.getIndex(), request.getPreviousHash().toByteArray(),
                new Timestamp(request.getTimestamp()), request.getData().toByteArray());
        block.setHash(request.getHash().toByteArray());
        blockService.addBlock(block);
        responseObserver.onNext(request);
        responseObserver.onCompleted();
    }
}



