package me.saechimdaeki.springsecurityoauth2.oauth2Client.controller

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.time.Clock
import java.time.Duration
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class Oauth2LoginController(
    private val oAuth2AuthorizedClientManager: DefaultOAuth2AuthorizedClientManager,
    private val oAuth2AuthorizedClientRepository: OAuth2AuthorizedClientRepository,
) {
    private val clockSkew = Duration.ofSeconds(3600)
    private val clock = Clock.systemUTC()


    private fun hasTokenExpired(accessToken: OAuth2AccessToken?): Boolean {
        return this.clock.instant().isAfter(accessToken?.expiresAt?.minus(this.clockSkew))
    }

    @GetMapping("/oauth2Login")
    fun oauth2Login(model: Model, request: HttpServletRequest, response: HttpServletResponse): String {

        val authentication = SecurityContextHolder.getContext().authentication

        val authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("keycloak")
            .principal(authentication)
            .attribute(HttpServletRequest::class.java.name, request)
            .attribute(HttpServletResponse::class.java.name, response)
            .build()

        val authorizedClient = oAuth2AuthorizedClientManager.authorize(authorizeRequest)


        // refreshToken 권한 부여 방식
        // 권한 부여 타입을 변경하지 않고 설정
        authorizedClient?.let {
            if (hasTokenExpired(it.accessToken) && it.refreshToken != null) {
                oAuth2AuthorizedClientManager.authorize(authorizeRequest)
            }
        }
        // 권한 부여 타입을 변경하고 실행
        authorizedClient?.let {
            if (hasTokenExpired(it.accessToken) && it.refreshToken != null) {

                val clientRegistration =
                    ClientRegistration.withClientRegistration(it.clientRegistration).authorizationGrantType(
                        AuthorizationGrantType.REFRESH_TOKEN
                    ).build()

                val oAuth2AuthorizedClient = OAuth2AuthorizedClient(
                    clientRegistration,
                    authorizedClient.principalName,
                    authorizedClient.accessToken,
                    authorizedClient.refreshToken
                )

                val authorizeRequest2 = OAuth2AuthorizeRequest
                    .withClientRegistrationId("keycloak")
                    .principal(authentication)
                    .attribute(HttpServletRequest::class.java.name, request)
                    .attribute(HttpServletResponse::class.java.name, response)
                    .build()

                oAuth2AuthorizedClientManager.authorize(authorizeRequest2)
            }
        }


        model.addAttribute("oAuth2AuthenticationToken", authorizedClient?.accessToken?.tokenValue)


        // Client Credentials 방식
//        model.addAttribute("oAuth2AuthenticationToken", authorizedClient?.accessToken?.tokenValue)

        //  Resource Owner Password 방식
//       authorizedClient?.let {
//            val oAuth2UserService = DefaultOAuth2UserService()
//            val clientRegistration = authorizedClient.clientRegistration
//            val accessToken = authorizedClient.accessToken
//            val oAuth2UserRequest = OAuth2UserRequest(clientRegistration, accessToken)
//            val oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest)
//
//            val authorityMapper = SimpleAuthorityMapper()
//            authorityMapper.setPrefix("SYSTEM_")
//            val grantedAuthorities = authorityMapper.mapAuthorities(oAuth2User.authorities)
//
//            val oAuth2AuthenticationToken =
//                OAuth2AuthenticationToken(oAuth2User, grantedAuthorities, clientRegistration.registrationId)
//
//            SecurityContextHolder.getContext().authentication = oAuth2AuthenticationToken
//
//            model.addAttribute("oAuth2AuthenticationToken", oAuth2AuthenticationToken)
//        }


        return "redirect:/"
    }

    @GetMapping("/logout")
    fun logout(authentication: Authentication, request: HttpServletRequest, response: HttpServletResponse) :String {
        val logoutHandler = SecurityContextLogoutHandler()
        logoutHandler.logout(request, response, authentication)
        return "redirect:/"
    }
}