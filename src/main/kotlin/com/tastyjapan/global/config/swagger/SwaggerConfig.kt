package com.tastyjapan.global.config.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {
    @Bean
    fun swaggerApi(): Docket = Docket(DocumentationType.OAS_30)
        .useDefaultResponseMessages(true)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.tastyjapan"))
        .paths(PathSelectors.ant("/**"))
        .build();


    private fun apiInfo() = ApiInfoBuilder()
        .title("TastyJapan Rest API Documentation")
        .description("TastyJapan Rest API Documentation")
        .version("1.0.0")
        .build()
}