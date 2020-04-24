package com.kingbom.spring.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class LoggingGlobalFiltersConfigurations implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Global Pre Filter executed");
        log.info("attributes         : {}", exchange.getAttributes());
        return chain.filter(exchange);
    }

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
              log.info("attributes         : {}", exchange.getAttributes());
             return chain.filter(exchange).then(Mono.fromRunnable(() -> log.info("Global Post Filter executed")));
        };
    }
}
