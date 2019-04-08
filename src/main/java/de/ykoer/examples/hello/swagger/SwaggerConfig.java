package de.ykoer.examples.hello.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/**
 * @author Yusuf Koer
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties({ SwaggerProperties.class })
public class SwaggerConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Value("${application.version}")
    private String applicationVersion;

    @Value("${keycloak.auth-server-url}")
    private String keycloakAuthServerUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${keycloak.resource}")
    private String keycloaResource;

    @Bean
    public Docket swaggerApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.ykoer.examples.hello.controller"))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .apiInfo(getApiInfo())
                .securitySchemes(securitySchema())
                .securityContexts(securityContext());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .version(swaggerProperties.getVersion())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .build();
    }

    private List<OAuth> securitySchema() {
        String keycloakRealmUrl = keycloakAuthServerUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/";
        TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint(keycloakRealmUrl + "auth", "swagger-ui", null);
        TokenEndpoint tokenEndpoint = new TokenEndpoint(keycloakRealmUrl + "token", "access_token");
        AuthorizationCodeGrant authorizationCodeGrant = new AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint);
        return Arrays.asList(new OAuth("OAuth2", Arrays.asList(), Arrays.asList(authorizationCodeGrant)));
    }

    private List<SecurityContext> securityContext() {
        SecurityContext securityContext = SecurityContext
                .builder()
                .securityReferences(defaultAuth())
                .build();
        return Arrays.asList(securityContext);
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[0];
        return Arrays.asList(new SecurityReference("OAuth2", authorizationScopes));
    }

    /**
     * A bean to configure the values that will be returned at the
     * /swagger-resources/configuration/security rest endpoint to configure the
     * Swagger UI frontend of SpringFox
     *
     * @return The security configuration for swagger UI
     */
    @Bean
    SecurityConfiguration security() {
        return SecurityConfigurationBuilder
                .builder()
                .realm(keycloakRealm)
                .clientId(keycloaResource)
                .appName(swaggerProperties.getTitle())
                .build();
    }
}