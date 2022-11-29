package me.saechimdaeki.springsecurityoauth2.service

import me.saechimdaeki.springsecurityoauth2.converters.ProviderUserConverter
import me.saechimdaeki.springsecurityoauth2.converters.ProviderUserRequest
import me.saechimdaeki.springsecurityoauth2.model.ProviderUser
import me.saechimdaeki.springsecurityoauth2.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.stereotype.Service

@Service
abstract class AbstractOAuth2UserService(
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val providerUserConverter: ProviderUserConverter<ProviderUserRequest, ProviderUser>
) {

    private val log = LoggerFactory.getLogger(AbstractOAuth2UserService::class.java)

    protected fun providerUser(providerUserRequest: ProviderUserRequest): ProviderUser? {
        return providerUserConverter.converter(providerUserRequest)
    }

    protected fun register(providerUser: ProviderUser?, userRequest: OAuth2UserRequest) {
        providerUser?.let {
            when (val user = userRepository.findByUsername(it.getUsername())) {
                null -> {
                    val registrationId = userRequest.clientRegistration.registrationId
                    userService.register(registrationId, providerUser)
                }
                else -> {
                    log.info("user = {}",user)
                }
            }
        }
    }
}