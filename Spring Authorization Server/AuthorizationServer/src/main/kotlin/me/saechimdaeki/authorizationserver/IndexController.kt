package me.saechimdaeki.authorizationserver

import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class IndexController {
    @GetMapping("/introspect")
    fun index(
        authentication: Authentication,
        @AuthenticationPrincipal principal: OAuth2AuthenticatedPrincipal
    ): OpaqueDto {
        val bearerTokenAuthentication = authentication as BearerTokenAuthentication
        val tokenAttributes = bearerTokenAuthentication.tokenAttributes
        val active = tokenAttributes["active"] as Boolean
        return OpaqueDto(active = active, authentication = bearerTokenAuthentication, principal = principal)
    }
}