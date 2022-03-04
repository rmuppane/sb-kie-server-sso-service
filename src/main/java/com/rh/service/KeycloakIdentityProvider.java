package com.rh.service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.kie.internal.identity.IdentityProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class KeycloakIdentityProvider {
    
    private static final Logger logger = LoggerFactory.getLogger(KeycloakIdentityProvider.class);

    @Bean
    public IdentityProvider identityProvider() {
        
        return new IdentityProvider() {

            @Override
            public boolean hasRole(String role) {
                List<String> keycloakRoles = getRoles();
                return (keycloakRoles != null && keycloakRoles.contains(role));
            }
            
            @Override
            public List<String> getRoles() {
                List<String> roles = new ArrayList<>();
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                
                if (auth != null && auth.isAuthenticated() && auth instanceof KeycloakAuthenticationToken) {
                    roles = ((KeycloakAuthenticationToken) auth).getAuthorities()
                                                                .stream()
                                                                .map(GrantedAuthority::getAuthority)
                                                                .collect(Collectors.toList());
                } 
                logger.debug("getRoles: {}", roles);
                return roles;
            }

            
            @Override
            public String getName() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String name = "";
                if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof KeycloakPrincipal) {
                    name = ((KeycloakPrincipal<?>) auth.getPrincipal()).getName();
                }
                logger.debug("getName: {}", name);
                return name;
            }
        };
    }

}