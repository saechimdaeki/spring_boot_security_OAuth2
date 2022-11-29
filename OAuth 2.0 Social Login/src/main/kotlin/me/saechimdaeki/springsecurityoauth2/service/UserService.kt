package me.saechimdaeki.springsecurityoauth2.service

import me.saechimdaeki.springsecurityoauth2.model.ProviderUser
import me.saechimdaeki.springsecurityoauth2.model.users.User
import me.saechimdaeki.springsecurityoauth2.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun register(registrationId:String, providerUser: ProviderUser) {
        val user = User(
            registrationId = registrationId,
            username = providerUser.getUsername(),
            provider = providerUser.getProvider(),
            email = providerUser.getEmail(),
            authorities = providerUser.getAuthorities(),
            id = providerUser.getId()
        )

    }
}