package me.saechimdaeki.authorizationserver

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.util.StringUtils
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class AuthorizationServerConfig(
    private val customAuthenticationProvider : CustomAuthenticationProvider
) {

    @Bean
    fun authSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {

        val authenticationConfigurer = OAuth2AuthorizationServerConfigurer<HttpSecurity>()

        val endpointsMatcher = authenticationConfigurer.endpointsMatcher

        authenticationConfigurer.authorizationEndpoint { authorizationEndpoint ->
            authorizationEndpoint
                .authenticationProvider(customAuthenticationProvider)
                .authorizationResponseHandler { _, response, authentication ->
                    val authentication1 =
                        authentication as OAuth2AuthorizationCodeRequestAuthenticationToken
                    println(authentication)
                    val redirectUri = authentication1.redirectUri
                    val authorizationCode = authentication1.authorizationCode!!.tokenValue
                    var state: String? = null
                    if (StringUtils.hasText(authentication1.state)) {
                        state = authentication1.state
                    }
                    response.sendRedirect("$redirectUri?code=$authorizationCode&state=$state")
                }
                .errorResponseHandler { _, response, exception ->
                    println(exception.toString())
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST)
                }
        }


        http.requestMatcher(endpointsMatcher)
            .authorizeRequests { req -> req.anyRequest().authenticated() }
            .csrf { csrf -> csrf.ignoringRequestMatchers(endpointsMatcher) }
            .apply(authenticationConfigurer)



        http.exceptionHandling { ex -> ex.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login")) }


        return http.build()
    }




}