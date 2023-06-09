package com.shopastro.sps.open.sample.gateway;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Component
public class GraphqlRouter implements RouteLocator {

    final RouteLocatorBuilder builder;

    public GraphqlRouter(RouteLocatorBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Flux<Route> getRoutes() {
        return builder.routes()
//                .route("graphql",
//                        p -> p.path("/graphql")
//                                .uri("http://localhost:8080")
//                )
                .build().getRoutes();
    }
}
