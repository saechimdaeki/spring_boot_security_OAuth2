package me.saechimdaeki.springsecurityoauth2

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrations
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class OAuth2ClientConfig {

    @Bean
    fun clientRegistrationRepository() = InMemoryClientRegistrationRepository(keycloakClientRegistration())

    private fun keycloakClientRegistration(): ClientRegistration {
        return ClientRegistrations.fromIssuerLocation("http://localhost:8080/realms/oauth2")
            .registrationId("keycloak")
            .clientId("oauth2-client-app")
            .clientSecret("IUcUxC5no8zlGo7aWGLzdBguo92BWCV3")
            .redirectUri("http://localhost:8081/login/oauth2/code/keycloak")
            .build()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity) : SecurityFilterChain {
        http.authorizeRequests().anyRequest().authenticated()
        http.oauth2Login()
        return http.build()
    }
}