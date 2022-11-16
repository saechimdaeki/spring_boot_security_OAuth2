package me.saechimdaeki.springsecurityoauth2

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.web.SecurityFilterChain

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
class OAuth2ClientConfig(
    private val clientRegistrationRepository: ClientRegistrationRepository,
) {

    @Bean
    fun oauth2SecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeRequests { authRequest -> authRequest.anyRequest().authenticated() }
            .oauth2Login(Customizer.withDefaults())
            .oauth2Client(Customizer.withDefaults())

        return http.build()
    }


//    @Bean
//    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
//        http.authorizeRequests { authRequest ->
//            authRequest.antMatchers("/home").permitAll()
//                .anyRequest().authenticated()
//        }
//
//        http.oauth2Login { authLogin ->
//            authLogin.authorizationEndpoint { authEndpoint ->
//                authEndpoint.authorizationRequestResolver(customOAuth2AuthorizationRequestResolver())
//            }
//        }
//
//        http.logout().logoutSuccessUrl("/home")
//        return http.build()
//    }
//
//    private fun customOAuth2AuthorizationRequestResolver(): OAuth2AuthorizationRequestResolver? {
//        return CustomOAuth2AuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/authorization")
//    }



//
//    private fun oidcLogoutSuccessHandler(): LogoutSuccessHandler? {
//
//        val successHandler =
//            OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository)
//
//        successHandler.setPostLogoutRedirectUri("http://localhost:8081/login")
//        return successHandler
//    }


}