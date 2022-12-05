package me.saechimdaeki.resource

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class OAuth2ResourceServerConfig(
    private val properties: OAuth2ResourceServerProperties,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { request -> request.anyRequest().authenticated() }
        http.oauth2ResourceServer { oAuth2ResourceServerConfig -> oAuth2ResourceServerConfig.jwt() }
        return http.build()
    }

    @Bean
    fun jwtDecoder1(): JwtDecoder {
        return JwtDecoders.fromIssuerLocation(properties.jwt.issuerUri)
    }

    @Bean
    fun jwtDecoder2(): JwtDecoder {
        return JwtDecoders.fromOidcIssuerLocation(properties.jwt.issuerUri)
    }

    @Bean
    fun jwtDecoder3(): JwtDecoder {
        return NimbusJwtDecoder.withJwkSetUri(properties.jwt.jwkSetUri)
            .jwsAlgorithm(SignatureAlgorithm.RS512).build()
    }
}