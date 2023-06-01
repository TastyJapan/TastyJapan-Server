package com.tastyjapan.global.config.swagger

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType
import org.springframework.boot.actuate.endpoint.ExposableEndpoint
import org.springframework.boot.actuate.endpoint.web.*
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.util.StringUtils
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.RequestParameterBuilder
import springfox.documentation.schema.ScalarType
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ParameterType
import springfox.documentation.service.RequestParameter
import springfox.documentation.service.Server
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {
    @Value("\${swagger.url}")
    private lateinit var baseUrl: String
    @Bean
    fun swaggerApi(): Docket = Docket(DocumentationType.OAS_30)
//        .ignoredParameterTypes(AuthenticationPrincipal::class.java)
        .servers(serverInfo())
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

    private fun serverInfo(): Server {
        return Server("", baseUrl, "", emptyList(), emptyList())
    }

    @Bean
    fun webEndpointServletHandlerMapping(
        webEndpointsSupplier: WebEndpointsSupplier,
        servletEndpointsSupplier: ServletEndpointsSupplier,
        controllerEndpointsSupplier: ControllerEndpointsSupplier,
        endpointMediaTypes: EndpointMediaTypes,
        corsProperties: CorsEndpointProperties,
        webEndpointProperties: WebEndpointProperties,
        environment: Environment
    ): WebMvcEndpointHandlerMapping {
        val allEndpoints: MutableList<ExposableEndpoint<*>> = ArrayList()
        val webEndpoints = webEndpointsSupplier.endpoints
        allEndpoints.addAll(webEndpoints)
        allEndpoints.addAll(servletEndpointsSupplier.endpoints)
        allEndpoints.addAll(controllerEndpointsSupplier.endpoints)
        val basePath = webEndpointProperties.basePath
        val endpointMapping = EndpointMapping(basePath)
        val shouldRegisterLinksMapping = shouldRegisterLinksMapping(webEndpointProperties, environment, basePath)
        return WebMvcEndpointHandlerMapping(
            endpointMapping,
            webEndpoints,
            endpointMediaTypes,
            corsProperties.toCorsConfiguration(),
            EndpointLinksResolver(allEndpoints, basePath),
            shouldRegisterLinksMapping,
            null
        )
    }

    private fun shouldRegisterLinksMapping(
        webEndpointProperties: WebEndpointProperties,
        environment: Environment,
        basePath: String
    ): Boolean {
        return webEndpointProperties.discovery.isEnabled &&
                (StringUtils.hasText(basePath) ||
                        ManagementPortType.get(environment) == ManagementPortType.DIFFERENT)
    }
}