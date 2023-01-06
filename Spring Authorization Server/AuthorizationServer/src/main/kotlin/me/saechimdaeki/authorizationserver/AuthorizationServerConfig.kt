package me.saechimdaeki.authorizationserver

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.util.StringUtils
import javax.servlet.http.HttpServletResponse


@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
class AuthorizationServerConfig(
    private val customAuthenticationProvider : CustomAuthenticationProvider,
    private val properties: OAuth2ResourceServerProperties,
) {
//
    @Bean
    fun nimbusOpaqueTokenIntrospector(): OpaqueTokenIntrospector? {
        val opaquetoken = properties.opaquetoken
        return NimbusOpaqueTokenIntrospector(
            opaquetoken.introspectionUri,
            opaquetoken.clientId,
            opaquetoken.clientSecret
        )
    }

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

        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer<*>::opaqueToken)


        http.exceptionHandling { ex -> ex.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login")) }


        return http.build()
    }




}