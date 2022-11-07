package me.saechimdaeki.springsecurityoauth2

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
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
}