package me.saechimdaeki.springsecurityoauth2.oauth2Client.controller

import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class Oauth2LoginController(
    private val oAuth2AuthorizedClientManager: DefaultOAuth2AuthorizedClientManager,
    private val oAuth2AuthorizedClientRepository: OAuth2AuthorizedClientRepository,
) {

    @GetMapping("/oauth2Login")
    fun oauth2Login(model:Model, request:HttpServletRequest, response: HttpServletResponse): String {

        val authentication = SecurityContextHolder.getContext().authentication

        val authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("keycloak")
            .principal(authentication)
            .attribute(HttpServletRequest::class.java.name, request)
            .attribute(HttpServletResponse::class.java.name, response)
            .build()

        val authorizedClient = oAuth2AuthorizedClientManager.authorize(authorizeRequest)

        authorizedClient?.let {
            val oAuth2UserService = DefaultOAuth2UserService()
            val clientRegistration = authorizedClient.clientRegistration
            val accessToken = authorizedClient.accessToken
            val oAuth2UserRequest = OAuth2UserRequest(clientRegistration, accessToken)
            val oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest)

            val authorityMapper = SimpleAuthorityMapper()
            authorityMapper.setPrefix("SYSTEM_")
            val grantedAuthorities = authorityMapper.mapAuthorities(oAuth2User.authorities)

            val oAuth2AuthenticationToken =
                OAuth2AuthenticationToken(oAuth2User, grantedAuthorities, clientRegistration.registrationId)

            SecurityContextHolder.getContext().authentication = oAuth2AuthenticationToken

            model.addAttribute("oAuth2AuthenticationToken", oAuth2AuthenticationToken)
        }


        return "redirect:/"
    }

    @GetMapping("/logout")
    fun logout(authentication: Authentication, request: HttpServletRequest, response: HttpServletResponse) :String {
        val logoutHandler = SecurityContextLogoutHandler()
        logoutHandler.logout(request, response, authentication)
        return "redirect:/"
    }
}