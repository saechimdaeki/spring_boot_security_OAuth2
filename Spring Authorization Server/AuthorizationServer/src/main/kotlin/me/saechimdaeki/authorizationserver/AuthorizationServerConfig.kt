package me.saechimdaeki.authorizationserver

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint


@Configuration
@EnableWebSecurity
class AuthorizationServerConfig {

    @Bean
    fun authSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {

        val authenticationConfigurer = OAuth2AuthorizationServerConfigurer<HttpSecurity>()

        val endpointsMatcher = authenticationConfigurer.endpointsMatcher

        authenticationConfigurer.authorizationEndpoint { endpoint ->
            endpoint.authorizationResponseHandler { request, response, authentication ->
                {

                }
            }
                .errorResponseHandler { request, response, exception ->
                }
//                .authenticationProvider(null)
        }


        http.requestMatcher(endpointsMatcher)
            .authorizeRequests { req -> req.anyRequest().authenticated() }
            .csrf { csrf -> csrf.ignoringRequestMatchers(endpointsMatcher) }
            .apply(authenticationConfigurer)



        http.exceptionHandling { ex -> ex.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login")) }


        return http.build()
    }




}