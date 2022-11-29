package me.saechimdaeki.springsecurityoauth2.service

import me.saechimdaeki.springsecurityoauth2.converters.ProviderUserConverter
import me.saechimdaeki.springsecurityoauth2.converters.ProviderUserRequest
import me.saechimdaeki.springsecurityoauth2.model.ProviderUser
import me.saechimdaeki.springsecurityoauth2.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    userRepository: UserRepository,
    userService: UserService,
    providerUserConverter: ProviderUserConverter<ProviderUserRequest, ProviderUser>
) : AbstractOAuth2UserService(
    userRepository,
    userService,
    providerUserConverter
), OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val clientRegistration = userRequest.clientRegistration
        val oAuth2UserService = DefaultOAuth2UserService()
        val oAuth2User = oAuth2UserService.loadUser(userRequest)


        val providerUserRequest = ProviderUserRequest(clientRegistration, oAuth2User)

        val providerUser = providerUser(providerUserRequest)

        super.register(providerUser, userRequest)

        return oAuth2User
    }
}