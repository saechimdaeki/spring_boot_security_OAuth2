package me.saechimdaeki.springsecurityoauth2

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
class OAuth2ClientConfig(
    private val clientRegistrationRepository: ClientRegistrationRepository,
) {

    @Bean
    fun oauth2SecurityFilterChain(http: HttpSecurity) : SecurityFilterChain {
        http.authorizeRequests { requests -> requests.anyRequest().authenticated() }
        http.oauth2Login(Customizer.withDefaults())
        http.logout()
            .logoutSuccessHandler(oidcLogoutSuccessHandler())
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID")

        return http.build()
    }

    private fun oidcLogoutSuccessHandler(): LogoutSuccessHandler? {

        val successHandler =
            OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository)

        successHandler.setPostLogoutRedirectUri("http://localhost:8081/login")
        return successHandler
    }
}