package com.ivoyant.bookmymovie.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info().title("Book my movie app using spring boot").summary("This application contains rest Api's which are useful to make request to register user, adding movies to catalog and user can able to view all and particular movie from catalog by sending appropriate request and also can able book ticket for particular movie and he can able to view the purchased ticket details"));

    }
}
