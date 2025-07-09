package com.valkyrie.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class ApiGateway {

    @Bean
    public RouterFunction<ServerResponse> gatewayRoutes() {
        RouterFunction<ServerResponse> productGetRoute = route("product-service-get")
                .GET("/product/**", http()).before(uri("http://localhost:8081")).build();

        RouterFunction<ServerResponse> productPostRoute = route("product-service-post")
                .POST("/product/**", http()).before(uri("http://localhost:8081")).build();

        RouterFunction<ServerResponse> orderPostRoute = route("order-service-post")
                .POST("/order/**", http()).before(uri("http://localhost:8082")).build();

        RouterFunction<ServerResponse> inventoryGetRoute = route("inventory-service-get")
                .GET("/inventory/**", http()).before(uri("http://localhost:8083")).build();

        RouterFunction<ServerResponse> inventoryPostRoute = route("inventory-service-post")
                .POST("/inventory/**", http()).before(uri("http://localhost:8083")).build();

        RouterFunction<ServerResponse> authRoutes = route("auth-service")
                .POST("/user/**", http()).before(uri("http://localhost:8085")).build();

        return productGetRoute.and(productPostRoute).and(orderPostRoute)
                .and(inventoryGetRoute).and(inventoryPostRoute).and(authRoutes);
    }
}
