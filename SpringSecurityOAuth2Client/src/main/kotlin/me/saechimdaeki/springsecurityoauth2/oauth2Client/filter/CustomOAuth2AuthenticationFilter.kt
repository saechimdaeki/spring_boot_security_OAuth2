package me.saechimdaeki.springsecurityoauth2.oauth2Client.filter

import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.core.OAuth2Token
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import java.time.Clock
import java.time.Duration
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class CustomOAuth2LoginAuthenticationFilter(
    private val oAuth2AuthorizedClientManager: DefaultOAuth2AuthorizedClientManager,
    private val authorizedClientRepository: OAuth2AuthorizedClientRepository
) :
    AbstractAuthenticationProcessingFilter(DEFAULT_FILTER_PROCESSES_URI) {
    private val authorizationSuccessHandler: OAuth2AuthorizationSuccessHandler
    private val clockSkew = Duration.ofSeconds(3600)
    private val clock = Clock.systemUTC()

    init {
        authorizationSuccessHandler =
            OAuth2AuthorizationSuccessHandler { authorizedClient: OAuth2AuthorizedClient?, authentication: Authentication?, attributes: Map<String?, Any?> ->
                authorizedClientRepository
                    .saveAuthorizedClient(
                        authorizedClient, authentication,
                        attributes[HttpServletRequest::class.java.name] as HttpServletRequest?,
                        attributes[HttpServletResponse::class.java.name] as HttpServletResponse?
                    )
            }
        oAuth2AuthorizedClientManager.setAuthorizationSuccessHandler(authorizationSuccessHandler)
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication? {
        var authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null) {
            authentication = AnonymousAuthenticationToken(
                "anonymous",
                "anonymousUser",
                AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")
            )
        }
        val authorizeRequest = OAuth2AuthorizeRequest
            .withClientRegistrationId("keycloak")
            .principal(authentication)
            .attribute(HttpServletRequest::class.java.name, request)
            .attribute(HttpServletResponse::class.java.name, response)
            .build()
        val oAuth2AuthorizedClient = oAuth2AuthorizedClientManager.authorize(authorizeRequest)
        if (oAuth2AuthorizedClient != null) {
            val clientRegistration = oAuth2AuthorizedClient.clientRegistration
            val accessToken = oAuth2AuthorizedClient.accessToken
            val refreshToken = oAuth2AuthorizedClient.refreshToken
            val oAuth2UserService = DefaultOAuth2UserService()
            val oauth2User =
                oAuth2UserService.loadUser(OAuth2UserRequest(oAuth2AuthorizedClient.clientRegistration, accessToken))
            val simpleAuthorityMapper = SimpleAuthorityMapper()
            val authorities: Collection<GrantedAuthority?> =
                simpleAuthorityMapper.mapAuthorities(oauth2User.authorities)
            val oAuth2AuthenticationToken =
                OAuth2AuthenticationToken(oauth2User, authorities, clientRegistration.registrationId)
            authorizationSuccessHandler.onAuthorizationSuccess(
                oAuth2AuthorizedClient,
                oAuth2AuthenticationToken,
                createAttributes(request, response)
            )
            return oAuth2AuthenticationToken
        }
        return null
    }

    private fun hasTokenExpired(token: OAuth2Token): Boolean {
        return clock.instant().isAfter(token.expiresAt!!.minus(clockSkew))
    }

    companion object {
        const val DEFAULT_FILTER_PROCESSES_URI = "/oauth2Login/**"
        private fun createAttributes(
            servletRequest: HttpServletRequest,
            servletResponse: HttpServletResponse
        ): Map<String, Any> {
            val attributes: MutableMap<String, Any> = HashMap()
            attributes[HttpServletRequest::class.java.name] = servletRequest
            attributes[HttpServletResponse::class.java.name] = servletResponse
            return attributes
        }
    }
}