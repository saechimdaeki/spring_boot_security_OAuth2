package me.saechimdaeki.springsecurityoauth2.controller

import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController {

    @GetMapping("/")
    fun index(model: Model, authentication: Authentication, @AuthenticationPrincipal oAuth2User: OAuth2User) : String {
        val authenticationToken = authentication as OAuth2AuthenticationToken?
        authenticationToken?.let {
            val attributes = oAuth2User.attributes
            var userName = attributes["name"]

            if(it.authorizedClientRegistrationId.equals("naver")) {
                val response = attributes["response"] as Map<*, *>
                userName = response["name"]
            }
            model.addAttribute("user",userName)
        }
        return "index"
    }
}