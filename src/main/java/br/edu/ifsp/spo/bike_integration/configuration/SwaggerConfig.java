package br.edu.ifsp.spo.bike_integration.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	GroupedOpenApi indexApi() {
		return GroupedOpenApi.builder().group("Index").pathsToMatch("/app/**").build();
	}

	@Bean
	GroupedOpenApi eventoApi() {
		return GroupedOpenApi.builder().group("Evento").pathsToMatch("/v1/evento/**").build();
	}

	@Bean
	GroupedOpenApi emailApi() {
		return GroupedOpenApi.builder().group("Email").pathsToMatch("/v1/email/**").build();
	}

	@Bean
	GroupedOpenApi usuarioApi() {
		return GroupedOpenApi.builder().group("Usuario").pathsToMatch("/v1/usuario/**").build();
	}

	@Bean
	GroupedOpenApi tokenApi() {
		return GroupedOpenApi.builder().group("Token").pathsToMatch("/v1/token/**").build();
	}

	@Bean
	GroupedOpenApi loginApi() {
		return GroupedOpenApi.builder().group("Login").pathsToMatch("/v1/login/**").build();
	}

	@Bean
	GroupedOpenApi nivelHabilidadeApi() {
		return GroupedOpenApi.builder().group("NivelHabilidade").pathsToMatch("/v1/nivel-habilidade/**").build();
	}

	@Bean
	GroupedOpenApi tipoEventoApi() {
		return GroupedOpenApi.builder().group("TipoEvento").pathsToMatch("/v1/tipo-evento/**").build();
	}

	@Bean
	GroupedOpenApi empresaApi() {
		return GroupedOpenApi.builder().group("Empresa").pathsToMatch("/v1/empresa/**").build();
	}

	@Bean
	GroupedOpenApi enderecoApi() {
		return GroupedOpenApi.builder().group("Endereco").pathsToMatch("/v1/endereco/**").build();
	}

	@Bean
	GroupedOpenApi infraestruturaApi() {
		return GroupedOpenApi.builder().group("Infraestrutura").pathsToMatch("/v1/infraestrutura/**").build();
	}

	@Bean
	OpenAPI customOpenAPI(BuildProperties buildProperties) {
		return new OpenAPI()
				.info(new Info().title(buildProperties.getName()).version(buildProperties.getVersion())
						.description("Documentação da API Bike Integration"))
				.components(new Components().addSecuritySchemes("AccessToken",
						new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER)
								.name("access-token")))
				.addSecurityItem(new SecurityRequirement().addList("AccessToken"));
	}
}
