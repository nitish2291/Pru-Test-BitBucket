package de.ykoer.examples.hello.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

@Configuration
@EnableResourceServer
@EnableWebSecurity(debug = false)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.client.clientId}")
    private String resourceId;

    @Value("${security.oauth2.resource.jwk.key-set-uri}")
    private String jwks;


    @Override
    public void configure(ResourceServerSecurityConfigurer config) throws Exception {
        config.resourceId(resourceId);
        config.tokenServices(tokenServices()).resourceId(resourceId);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() throws Exception {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(jwkTokenStore());
        return defaultTokenServices;
    }

    @Bean
    public TokenStore jwkTokenStore() throws Exception {
        return new JwkTokenStore(jwks);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // Disable CSRF, we're a microservice not a website.
                .csrf().disable()

                //.anonymous().disable()

                .authorizeRequests()

                // Allow anonymous OPTIONS request
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                .antMatchers("/**").permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
