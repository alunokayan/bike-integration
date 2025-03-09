package br.edu.ifsp.spo.bike_integration.configuration;

import java.util.Arrays;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	private static final String LICENSE_NAME = "";
	private static final String LICENSE_URL = "";
	private static final String API_TITLE = "BIKE-INTEGRATION";
	private static final String API_DESCRIPTION = "Serviços para integração com ao app Bicity";

	@Value("${server.servlet.context-path}")
	private String contextPath;

	private final BuildProperties buildProperties;

	public OpenApiConfig(final BuildProperties buildProperties) {
		this.buildProperties = buildProperties;
	}

	@Bean
	GroupedOpenApi auth() {
		return GroupedOpenApi.builder().group("auth").pathsToMatch("/v1/auth/**")
				.addOpenApiCustomizer(openApi -> openApi.info(createInfo("1.0"))).build();
	}

	@Bean
	GroupedOpenApi v1Api() {
		return GroupedOpenApi.builder().group("v1").pathsToMatch("/v1/**").pathsToExclude("/v1/auth/**")
				.addOpenApiCustomizer(openApi -> openApi.info(createInfo("1.0"))).build();
	}

	@Bean
	OpenAPI configOpenApi() {
		final String securitySchemeName = "bearerAuth";
		return new OpenAPI()
				.info(new Info().title(API_TITLE).version(this.buildProperties.getVersion())
						.description(API_DESCRIPTION).license(new License().name(LICENSE_NAME).url(LICENSE_URL)))
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(new Components().addSecuritySchemes(securitySchemeName,
						new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer")
								.bearerFormat("JWT")))
				.servers(Arrays.asList(new Server().url(this.contextPath).description("Servidor Atual")));
	}

	/*
	 * PRIVATE METHODS
	 */
	private Info createInfo(String version) {
		return new Info().title(API_TITLE).version(version).description(API_DESCRIPTION)
				.license(new License().name(LICENSE_NAME).url(LICENSE_URL));
	}
}