package org.springframework.security.config.annotation.authentication.builders;

import org.springframework.security.config.ObjectPostProcessor;

public class AuthenticationManagerBuilderImpl extends AuthenticationManagerBuilder {
    public AuthenticationManagerBuilderImpl(ObjectPostProcessor<Object> objectPostProcessor) {
        super(objectPostProcessor);
    }
}
