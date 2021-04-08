package com.br.zup.proposta.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                authorizeRequests
                        .antMatchers(HttpMethod.GET, "/proposals/**").hasAuthority("SCOPE_proposals:read")
                        .antMatchers(HttpMethod.GET, "/cards/**").hasAuthority("SCOPE_cards:read")
                        .antMatchers(HttpMethod.POST, "/cards/**").hasAuthority("SCOPE_cards:write")
                        .antMatchers(HttpMethod.POST, "/proposals/**").hasAuthority("SCOPE_proposals:write")
                        .anyRequest().authenticated()
        )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

}