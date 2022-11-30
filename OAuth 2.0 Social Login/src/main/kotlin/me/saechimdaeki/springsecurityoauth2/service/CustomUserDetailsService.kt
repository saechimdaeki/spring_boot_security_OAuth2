package me.saechimdaeki.springsecurityoauth2.service

import me.saechimdaeki.springsecurityoauth2.converters.ProviderUserConverter
import me.saechimdaeki.springsecurityoauth2.converters.ProviderUserRequest
import me.saechimdaeki.springsecurityoauth2.model.PrincipalUser
import me.saechimdaeki.springsecurityoauth2.model.ProviderUser
import me.saechimdaeki.springsecurityoauth2.model.users.User
import me.saechimdaeki.springsecurityoauth2.repository.UserRepository
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    val userRepository: UserRepository,
    val userService: UserService,
   val  providerUserConverter: ProviderUserConverter<ProviderUserRequest, ProviderUser>
) : AbstractOAuth2UserService(userRepository, userService, providerUserConverter), UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? {
        return username?.let {
            var user = userRepository.findByUsername(it)

            if(user == null) {
                user = User(id="1", username = "user1", password = "{noop}1234", authorities = AuthorityUtils.createAuthorityList("ROLE_USER"), email = "user@a.com")
            }


            val providerUserRequest = ProviderUserRequest(user)

            val providerUser = providerUser(providerUserRequest)

            return PrincipalUser(providerUser!!)
        }

    }
}