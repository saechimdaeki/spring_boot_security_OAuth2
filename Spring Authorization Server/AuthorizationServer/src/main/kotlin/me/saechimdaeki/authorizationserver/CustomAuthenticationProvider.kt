package me.saechimdaeki.authorizationserver

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationProvider
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.stereotype.Component


@Component
class CustomAuthenticationProvider : AuthenticationProvider {
    private val registeredClientRepository: RegisteredClientRepository? = null
    private val oAuth2AuthorizationService: OAuth2AuthorizationService? = null
    private val oAuth2AuthorizationConsentService: OAuth2AuthorizationConsentService? = null

    override fun authenticate(authentication: Authentication): Authentication {
        val authorizationCodeRequestAuthentication = authentication as OAuth2AuthorizationCodeRequestAuthenticationToken
        val authenticationProvider = OAuth2AuthorizationCodeRequestAuthenticationProvider(
            registeredClientRepository,
            oAuth2AuthorizationService,
            oAuth2AuthorizationConsentService
        )
        val authenticate =
            authenticationProvider.authenticate(authorizationCodeRequestAuthentication) as OAuth2AuthorizationCodeRequestAuthenticationToken
        val principal: Authentication = authorizationCodeRequestAuthentication.principal as Authentication
        println("principal = $principal")
        return authenticate
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return OAuth2AuthorizationCodeRequestAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}