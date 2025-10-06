package com.tayyari.ApiGateway.filter;

import com.tayyari.ApiGateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Skip authentication for auth endpoints
            if (isPublicEndpoint(request.getURI().getPath())) {
                System.out.println("Inside Auth Endpoint checking for public endpoint");
                return chain.filter(exchange);
            }

            // Check if Authorization header exists
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                System.out.println("Inside Check if Authorization header exists");
                return unauthorizedResponse(exchange);
            }

            String authHeader = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("Inside Header check");
                return unauthorizedResponse(exchange);
            }

            String token = authHeader.substring(7);
            System.out.println("Token :" +token );


            try {
                if (!jwtUtil.validateToken(token)) {
                    System.out.println("Inside try  validate token check if block");
                    return unauthorizedResponse(exchange);
                }

                // Extract user info and add to headers
                String username = jwtUtil.extractUsername(token);
                System.out.println(" ****username from api gateway token :" + username);

                List<String> role = jwtUtil.extractRole(token);
                //String role = role1.substring(4);
                System.out.println(" ****ROLE from  token :" + role); // [ROLE_USER, ROLE_ADMIN]


                // Add user info to request headers for downstream services
                ServerHttpRequest modifiedRequest = request.mutate()
                        .header("X-User-Id", username)
                        .header("X-User-Role", String.valueOf(role))
                        .header(HttpHeaders.CONTENT_TYPE, request.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE))
                        .build();

                System.out.println("Modified request : " + modifiedRequest.toString());
                System.out.println( "**** Role from api gateway YML : "+config.getRequiredRoles());

                // Check role-based access if configured
                if (config.getRequiredRoles() != null && !config.getRequiredRoles().isEmpty()) {
                    System.out.println( "::::::::::"+config.getRequiredRoles());
                    System.out.println("Inside Check role-based access if configured");
                    if (role.stream().noneMatch(config.getRequiredRoles()::contains)) {
                        System.out.println("Inside Check role-based access if configured ::" +config.getRequiredRoles());
                        return forbiddenResponse(exchange);
                    }
                }
                System.out.println("Before filter chain continuing with : " +exchange.mutate().request(modifiedRequest).toString());
                return chain.filter(exchange.mutate().request(modifiedRequest).build());

            } catch (Exception e) {
                System.out.println("Inside catch block1");
                return unauthorizedResponse(exchange);

            }
        };
    }

    private boolean isPublicEndpoint(String path) {
        System.out.println("Inside isAuthEndpoint method body");
        return path.contains("/auth/") ||
                path.contains("/login") ||
                path.contains("/register") ||
                path.contains("/test") ||
                path.contains("/public");
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private Mono<Void> forbiddenResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

   /* public static class Config {
        private String requiredRole;

        public String getRequiredRole() {
            return requiredRole;
        }

        public void setRequiredRole(String requiredRole) {
            this.requiredRole = requiredRole;
        }
    }*/
   public static class Config {
       private List<String> requiredRoles = new ArrayList<>();
       public List<String> getRequiredRoles() { return requiredRoles; }
       public void setRequiredRoles(List<String> requiredRoles) { this.requiredRoles = requiredRoles; }
   }
}
