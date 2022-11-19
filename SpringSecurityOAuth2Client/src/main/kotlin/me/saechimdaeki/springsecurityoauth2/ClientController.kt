package me.saechimdaeki.springsecurityoauth2

import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest

@Controller
class ClientController(
    private val oAuth2AuthorizedClientRepository: OAuth2AuthorizedClientRepository,
    private val oAuth2AuthorizedClientService: OAuth2AuthorizedClientService
) {

    @GetMapping("/client")
    fun client(authentication: Authentication, request: HttpServletRequest, model: Model): String {

        val clientRegistrationId = "keycloak"

        val authorizedClient1 = oAuth2AuthorizedClientRepository.loadAuthorizedClient<OAuth2AuthorizedClient>(
            clientRegistrationId,
            authentication,
            request
        )

        val authorizedClient2 = oAuth2AuthorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
            clientRegistrationId, authentication.name
        )

        val accessToken = authorizedClient1.accessToken

        val oAuth2User =
            DefaultOAuth2UserService().loadUser(OAuth2UserRequest(authorizedClient1.clientRegistration, accessToken))

        val authenticationToken = OAuth2AuthenticationToken(
            oAuth2User,
            listOf(SimpleGrantedAuthority("ROLE_USER")),
            authorizedClient1.clientRegistration.registrationId
        )

        SecurityContextHolder.getContext().authentication = authenticationToken

        model.addAttribute("accessToken",accessToken.tokenValue)
        model.addAttribute("refreshToken",authorizedClient1.refreshToken?.tokenValue)
        model.addAttribute("principalName",oAuth2User.name)
        model.addAttribute("clientName",authorizedClient1.clientRegistration.clientName)
        return "cleint"
    }
}