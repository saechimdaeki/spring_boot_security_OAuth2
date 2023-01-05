package me.saechimdaeki.authorizationserver

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RegisteredClientController(private val registeredClientRepository: RegisteredClientRepository) {

    @GetMapping("/registeredClients")
    fun registeredClientList() : List<RegisteredClient?> {
        val registeredClient1 = registeredClientRepository.findByClientId("oauth2-client-app1")
        val registeredClient2 = registeredClientRepository.findByClientId("oauth2-client-app2")
        val registeredClient3 = registeredClientRepository.findByClientId("oauth2-client-app3")

        return listOf(registeredClient1,registeredClient2,registeredClient3)

    }
}