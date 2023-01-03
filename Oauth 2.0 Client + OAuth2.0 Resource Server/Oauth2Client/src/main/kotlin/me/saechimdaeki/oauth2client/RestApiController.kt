package me.saechimdaeki.oauth2client

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange


@RestController
class RestApiController(private val restTemplate: RestTemplate) {

    @GetMapping("/token")
    fun token(@RegisteredOAuth2AuthorizedClient("keycloak") oAuth2AuthorizedClient: OAuth2AuthorizedClient): OAuth2AccessToken {
        return oAuth2AuthorizedClient.accessToken
    }

    @GetMapping("/photos")
    fun photos(accessToken: AccessToken) : List<Photo>? {
        val headers = HttpHeaders()
        headers.add("Authorization","Bearer "+accessToken.token)
        val entity = HttpEntity<Any>(headers)
        val url = "http://localhost:8082/photos"

        val response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            object : ParameterizedTypeReference<List<Photo>>() {})

        return response.body
    }
}