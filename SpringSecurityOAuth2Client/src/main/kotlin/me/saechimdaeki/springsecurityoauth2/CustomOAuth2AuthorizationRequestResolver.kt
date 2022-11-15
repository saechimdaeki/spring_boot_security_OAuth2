package me.saechimdaeki.springsecurityoauth2

import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import javax.servlet.http.HttpServletRequest

class CustomOAuth2AuthorizationRequestResolver(
    private val clientRegistrationRepository: ClientRegistrationRepository,
    baseUri: String
) : OAuth2AuthorizationRequestResolver {

    private val defaultResolver: DefaultOAuth2AuthorizationRequestResolver
    private val REGISTRATION_ID_URI_VARIABLE_NAME = "registrationId"
    private val authorizationRequestMatcher: AntPathRequestMatcher
    private val DEFAULT_PKCE_APPLIER = OAuth2AuthorizationRequestCustomizers.withPkce()

    init {
        this.authorizationRequestMatcher = AntPathRequestMatcher(
            baseUri + "/{" + REGISTRATION_ID_URI_VARIABLE_NAME + "}"
        )
        this.defaultResolver = DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, baseUri)
    }


    override fun resolve(request: HttpServletRequest?): OAuth2AuthorizationRequest? {
        val registrationId = resolveRegistrationId(request) ?: return null


        val clientRegistration = clientRegistrationRepository.findByRegistrationId(registrationId)
        if (registrationId == "keycloakWithPKCE") {
            val oAuth2AuthorizationRequest = defaultResolver.resolve(request)
            return customResolve(oAuth2AuthorizationRequest, clientRegistration)
        }
        return defaultResolver.resolve(request)
    }

    private fun customResolve(
        oAuth2AuthorizationRequest: OAuth2AuthorizationRequest?,
        registrationId: ClientRegistration
    ): OAuth2AuthorizationRequest? {

        val map = mapOf<String, Any>(
            Pair("customName1", "customValue1"),
            Pair("customName2", "customValue2"),
            Pair("customName3", "customValue3")
        )

        val builder = OAuth2AuthorizationRequest.from(oAuth2AuthorizationRequest)
            .additionalParameters(map)
        DEFAULT_PKCE_APPLIER.accept(builder)

        return builder.build()

    }


    private fun resolveRegistrationId(request: HttpServletRequest?): String? {
        if (this.authorizationRequestMatcher.matches(request)) {
            return this.authorizationRequestMatcher.matcher(request).variables[REGISTRATION_ID_URI_VARIABLE_NAME]
        }
        return null
    }

    override fun resolve(request: HttpServletRequest?, clientRegistrationId: String?): OAuth2AuthorizationRequest? {
        val clientRegistration = clientRegistrationRepository.findByRegistrationId(clientRegistrationId)
        if (clientRegistrationId == "keycloakWithPKCE") {
            val oAuth2AuthorizationRequest = defaultResolver.resolve(request)
            return customResolve(oAuth2AuthorizationRequest, clientRegistration)
        }
        return defaultResolver.resolve(request, clientRegistrationId)
    }

}

