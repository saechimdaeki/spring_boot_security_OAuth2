package me.saechimdaeki.authorizationserver

import org.springframework.security.oauth2.core.OAuth2TokenType
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OAuth2AuthorizationController() {


//    @GetMapping("/authorization")
//    fun oauth2Authorization(token: String) : OAuth2Authorization? {
//
//        return oAuth2AuthorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN)
//
//    }
}