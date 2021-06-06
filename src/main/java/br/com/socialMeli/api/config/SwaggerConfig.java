package br.com.socialMeli.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private Contact contact() {
        return new Contact("Amedeo Elmo",
                "https://github.com/aelmo",
                "amedeo.elmo@mercadolivre.com");
    }

    private ApiInfoBuilder infoApi() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

        apiInfoBuilder.title("REST Api - Social Meli");
        apiInfoBuilder.description("Api for Social Meli");
        apiInfoBuilder.version("1.0");
        apiInfoBuilder.license("Private");
        apiInfoBuilder.contact(this.contact());

        return apiInfoBuilder;
    }

    private ApiInfoBuilder infoApiV2() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

        apiInfoBuilder.title("REST Api - Social Meli");
        apiInfoBuilder.description("Api for Social Meli - Complements");
        apiInfoBuilder.version("2.0");
        apiInfoBuilder.license("Private");
        apiInfoBuilder.contact(this.contact());

        return apiInfoBuilder;
    }

    @Bean
    public Docket swaggerSocialMeliApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("rest-socialMeli-api-1.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.socialMeli.api.controller"))
                .paths(PathSelectors.ant("/api/v1/**"))
                .build()
                .apiInfo(this.infoApi().build())
                .useDefaultResponseMessages(false);
    }

    @Bean
    public Docket swaggerSocialMeliApiV2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("rest-socialMeli-api-2.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.socialMeli.api.controller.v2"))
                .paths(PathSelectors.ant("/api/v2/**"))
                .build()
                .apiInfo(this.infoApiV2().build())
                .useDefaultResponseMessages(false);
    }
}
