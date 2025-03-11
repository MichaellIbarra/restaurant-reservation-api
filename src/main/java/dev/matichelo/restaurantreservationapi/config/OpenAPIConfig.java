package dev.matichelo.restaurantreservationapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.List;

// La anotación @Configuration indica que esta clase es una clase de configuración de Spring lo que es que se puede usar para configurar beans de Spring.

@Configuration
public class OpenAPIConfig {
    @Value("${matichelo.openapi.dev-url}")
    private String devUrl;
    @Value("${matichelo.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI openAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Development server");
        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Production server");

        Contact contact = new Contact();
        contact.setName("Matichelo Dev");
        contact.setEmail("michaell.ibarra@vallegrande.edu.pe");
        contact.setUrl("https://matichelo.dev");

        License mirLicense = new License().name("MIT").url("https://opensource.org/licenses/MIT");

        Info info = new Info();
        info.setTitle("Restaurant Reservation API");
        info.setDescription("API for restaurant reservation");
        info.setVersion("1.0.0");
        info.setContact(contact);
        info.setLicense(mirLicense);
        info.termsOfService("https://matichelo.dev/terms-of-service");

        SecurityScheme securityScheme = new SecurityScheme();
        // type es el tipo de esquema de seguridad que se está utilizando.
        // SecurityScheme.Type.HTTP indica que se está utilizando un esquema de seguridad HTTP.
        securityScheme.type(SecurityScheme.Type.HTTP);
        securityScheme.scheme("bearer");
        securityScheme.bearerFormat("JWT");
        securityScheme.name("Authorization");

        // Components es un objeto que contiene los esquemas de seguridad que se utilizan en la API.
        Components components = new Components();
        // addSecuritySchemes agrega un esquema de seguridad a la API.
        // bearerAuth es el nombre del esquema de seguridad.
        // securityScheme es el esquema de seguridad que se está agregando.
        components.addSecuritySchemes("bearerAuth", securityScheme);

        // SecurityRequirement es un objeto que representa un requisito de seguridad.
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer))
                .addSecurityItem(securityRequirement)
                .components(components);
    }


}
