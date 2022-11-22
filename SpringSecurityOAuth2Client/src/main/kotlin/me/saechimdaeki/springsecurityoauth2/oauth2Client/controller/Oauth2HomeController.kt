package me.saechimdaeki.springsecurityoauth2.oauth2Client.controller

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class Oauth2HomeController(
    private val oAuth2AuthorizedClientService: OAuth2AuthorizedClientService,
) {

    @GetMapping("/home")
    fun home(model:Model, oAuth2AuthenticationToken: OAuth2AuthenticationToken): String {

        val authorizedClient =
        oAuth2AuthorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>("keycloak",oAuth2AuthenticationToken.name)
        model.addAttribute("oAuth2AuthenticationToken", oAuth2AuthenticationToken);
        model.addAttribute("AccessToken", authorizedClient.accessToken.tokenValue);
        model.addAttribute("RefreshToken", authorizedClient.refreshToken?.tokenValue);

        return "home";
    }


}