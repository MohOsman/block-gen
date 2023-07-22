package se.mohosman.blockgen.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import se.mohosman.blockgen.service.BlockService;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class router {

    private BlockService blockService;

    @Autowired
    public router(BlockService blockService) {
        this.blockService = blockService;
    }

    @Bean
    public RouterFunction<ServerResponse> CreateBlock() {
        // TODO to be implementen
        return null;
}
}