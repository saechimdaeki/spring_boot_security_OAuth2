package me.saechimdaeki.resource.macAndRsa.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.net.URI

@RestController
class IndexController {

    @GetMapping("/api/user")
    fun index(authentication: Authentication, @AuthenticationPrincipal principal: Jwt): Authentication {

        val jwtAuthenticationToken = authentication as JwtAuthenticationToken
        val sub = jwtAuthenticationToken.tokenAttributes["sub"] as String?
        val email = jwtAuthenticationToken.tokenAttributes["email"] as String?
        val scope = jwtAuthenticationToken.tokenAttributes["scope"] as String?

        val sub1 = principal.getClaim<String>("sub")
        val token = principal.tokenValue
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()

        headers.add("Authorization", "Bearer $token")
        val request = RequestEntity<String>(headers, HttpMethod.GET, URI("http://localhost:8082"))
        val response = restTemplate.exchange(request, String::class.java)
        val body = response.body

        // for debugging

        return authentication
    }



}