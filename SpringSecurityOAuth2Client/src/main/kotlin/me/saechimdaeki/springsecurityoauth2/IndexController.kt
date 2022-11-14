package me.saechimdaeki.springsecurityoauth2

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController(
    private val clientRegistrationRepository: ClientRegistrationRepository,
) {
    val logger:Logger = LoggerFactory.getLogger(IndexController::class.java)

    @GetMapping("/")
    fun index() :String {

        val clientRegistration = clientRegistrationRepository.findByRegistrationId("keycloak")
        val clientId = clientRegistration.clientId
        logger.info("clientId = {}",clientId)

        val redirectUri = clientRegistration.redirectUri
        logger.info("redirectUri = {}",redirectUri)
        return "index"
    }

    @GetMapping("/user")
    fun user(authentication: Authentication): OAuth2User {
        val authentication1 = SecurityContextHolder.getContext().authentication as OAuth2AuthenticationToken
        val authentication2 = authentication as OAuth2AuthenticationToken

        val oAuth2User = authentication2.principal as OAuth2User
        return oAuth2User
    }

    @GetMapping("/oauth2User")
    fun oAuth2User(@AuthenticationPrincipal oAuth2User: OAuth2User) : OAuth2User {
        logger.info("oAUth2User = {}" , oAuth2User)
        return oAuth2User
    }

    @GetMapping("/oidcUser")
    fun oidcUser(@AuthenticationPrincipal oidcUser: OidcUser) : OidcUser {
        logger.info("oidcUser = {}" , oidcUser)
        return oidcUser
    }




//    @GetMapping("/user")
//    fun user(accessToken: String) : OAuth2User {
//        val clientRegistration = clientRegistrationRepository.findByRegistrationId("keycloak")
//
//        val oAuth2AccessToken =
//            OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, accessToken, Instant.now(), Instant.MAX)
//
//        val oAuth2UserRequest = OAuth2UserRequest(clientRegistration, oAuth2AccessToken)
//
//        val defaultOAuth2UserService = DefaultOAuth2UserService()
//
//        val oAuth2User = defaultOAuth2UserService.loadUser(oAuth2UserRequest)
//
//        return oAuth2User
//    }
//
//
//
//    @GetMapping("/oidc")
//    fun oidc(accessToken: String, idToken: String) : OAuth2User {
//        val clientRegistration = clientRegistrationRepository.findByRegistrationId("keycloak")
//
//        val oAuth2AccessToken =
//            OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, accessToken, Instant.now(), Instant.MAX)
//
//        val idTokenClaims = HashMap<String,Any>()
//
//        idTokenClaims.put(IdTokenClaimNames.ISS, "http://localhost:8080/realms/oauth2")
//        idTokenClaims.put(IdTokenClaimNames.SUB,"OIDC0")
//        idTokenClaims.put("preferred_username","user")
//
//        val oidcIdToken = OidcIdToken(idToken, Instant.now(), Instant.MAX, idTokenClaims)
//
//        val oAuth2UserRequest = OidcUserRequest(clientRegistration, oAuth2AccessToken, oidcIdToken)
//        val oidcUserService = OidcUserService()
//        val oAuth2User = oidcUserService.loadUser(oAuth2UserRequest)
//
//        return oAuth2User
//    }
}