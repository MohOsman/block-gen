package se.mohosman.blockgen.router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.mohosman.blockgen.model.Block;
import se.mohosman.blockgen.service.BlockService;

import java.util.LinkedList;
import java.util.List;

@Component
public class BlockHandler {

    @Autowired
    private final BlockService blockService;

    public BlockHandler(BlockService blockService) {
        this.blockService = blockService;
    }


    public Mono<ServerResponse> getBlocks(ServerRequest request) {
            // Get the Flux of blocks from the BlockServer
            Flux<Block> blocksFlux = blockService.getBlockChain();

            // Return the Flux of blocks in the response as a JSON array
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(blocksFlux, Block.class);
        }
}