package com.truckcompany.example.TruckCompany;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringFoxConfig {
    // @Bean
    // public OpenAPI api() {
    // return new OpenAPI()
    // .components(new Components()).;

    // // .select()
    // //
    // .apis(RequestHandlerSelectors.basePackage("com.truckcompany.example.TruckCompany.Controllers"))
    // // .paths(PathSelectors.any())
    // // .build();
    // }
    // }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("TruckCompany API")
                        .description("This is the swagger ui for the TruckCompany app"));
    }
}

// @Bean
// public UiConfiguration uiConfig() {
// return UiConfigurationBuilder.builder()
// .displayRequestDuration(true)
// .build();
// }
// }
