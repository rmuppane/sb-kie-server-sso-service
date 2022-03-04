package com.rh.service;

import org.jbpm.process.instance.event.listeners.VariableGuardProcessEventListener;
import org.kie.internal.identity.IdentityProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeycloakVariableGuardProcessEventListener extends VariableGuardProcessEventListener {

    public KeycloakVariableGuardProcessEventListener(@Value("${kie.restricted-role}")String requiredRole, IdentityProvider identityProvider) {
        super(requiredRole, identityProvider);
    }

}