package kr.kanzi.gatewaysvc.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

//@Configuration
@RequiredArgsConstructor
public class FilterConfig {
    private final Environment env;
    private final CustomFilter customFilter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder, CustomFilter customFilter) {

        return builder.routes()
                .route(r -> r.path("/user-svc/**")
                            .filters(f -> f.addRequestHeader("first-request", "first-request-header-by-java")
                                            .addResponseHeader("first-response", "first-response-header-by-java")
                                            .filter(customFilter.apply(new CustomFilter.Config()))
                            )
                            .uri("http://localhost:9001"))
                .build();
    }
}
