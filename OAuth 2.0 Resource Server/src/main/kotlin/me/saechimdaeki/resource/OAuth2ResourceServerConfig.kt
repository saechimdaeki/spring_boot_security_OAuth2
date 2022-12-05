package me.saechimdaeki.resource

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class OAuth2ResourceServerConfig {

    @Bean
    fun securityFilterChain(http:HttpSecurity) : SecurityFilterChain {
        http.authorizeHttpRequests { request -> request.anyRequest().authenticated() }
        http.oauth2ResourceServer {  oAuth2ResourceServerConfig -> oAuth2ResourceServerConfig.jwt() }
        return http.build()
    }
}