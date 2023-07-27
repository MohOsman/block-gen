package se.mohosman.blockgen.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import se.mohosman.blockgen.hash.HexUtils;
import se.mohosman.blockgen.model.Block;
import se.mohosman.blockgen.service.BlockService;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class BlockRouter {

    private BlockService blockService;
    private final Logger logger = LoggerFactory.getLogger(BlockRouter.class);
    // TODO modify this to use BLockHanlder instead
    @Autowired
    public BlockRouter(BlockService blockService) {
        this.blockService = blockService;
    }

    @Bean
    public RouterFunction<ServerResponse> CreateBlock(BlockHandler blockHandler) {
        return route(POST("/createBlock"), req -> req.bodyToMono(String.class)
                .doOnNext(data -> logger.info("Received Block Data {}", data))
                .flatMap(data -> {
                    byte[] result = blockService.createBlock(data);
                    String hashHex = HexUtils.bytesToHex(result).toUpperCase();
                    return ok()
                            .contentType(MediaType.TEXT_PLAIN)
                            .bodyValue(hashHex);
                })
        ).and(route(GET("/block/{hash}"), req ->{
            byte[] byteHash = HexUtils.hexToBytes(req.pathVariable("hash"));
            Mono<Block> block = blockService.getBlock(byteHash);
            return ok().contentType(MediaType.APPLICATION_JSON).body(block, Block.class);
        })).and(route(GET("/blocks"), blockHandler::getBlocks));

    }
}