package me.saechimdaeki.springsecurityoauth2.service

import me.saechimdaeki.springsecurityoauth2.model.GoogleUser
import me.saechimdaeki.springsecurityoauth2.model.KeycloakUser
import me.saechimdaeki.springsecurityoauth2.model.NaverUser
import me.saechimdaeki.springsecurityoauth2.model.ProviderUser
import me.saechimdaeki.springsecurityoauth2.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
abstract class AbstractOAuth2UserService(
    private val userRepository: UserRepository,
    protected val userService: UserService
) {

    private val log = LoggerFactory.getLogger(AbstractOAuth2UserService::class.java)

    protected fun providerUser(clientRegistration: ClientRegistration, oAuth2User: OAuth2User): ProviderUser? {
        val registrationId = clientRegistration.registrationId
        return when {
            registrationId.equals("keycloak") -> {
                KeycloakUser(oAuth2User, clientRegistration)
            }
            registrationId.equals("google") -> {
                GoogleUser(oAuth2User, clientRegistration)
            }
            registrationId.equals("naver") -> {
                NaverUser(oAuth2User, clientRegistration)
            }
            else -> null
        }
    }

    protected fun register(providerUser: ProviderUser?, userRequest: OAuth2UserRequest){
        providerUser?.let {
            when (val user = userRepository.findByUsername(it.getUsername())) {
                null -> {
                    val registrationId = userRequest.clientRegistration.registrationId
                    userService.register(registrationId,providerUser)
                }
                else -> {
                    log.info("user = {}",user)
                }
            }
        }
    }
}