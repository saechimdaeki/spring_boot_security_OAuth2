package me.saechimdaeki.springsecurityoauth2.service

import me.saechimdaeki.springsecurityoauth2.converters.ProviderUserConverter
import me.saechimdaeki.springsecurityoauth2.converters.ProviderUserRequest
import me.saechimdaeki.springsecurityoauth2.model.PrincipalUser
import me.saechimdaeki.springsecurityoauth2.model.ProviderUser
import me.saechimdaeki.springsecurityoauth2.repository.UserRepository
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service

@Service
class CustomOidcUserService(userRepository: UserRepository, userService: UserService, providerUserConverter:ProviderUserConverter<ProviderUserRequest, ProviderUser>) : AbstractOAuth2UserService(
    userRepository,
    userService,
    providerUserConverter
), OAuth2UserService<OidcUserRequest, OidcUser> {
    override fun loadUser(userRequest: OidcUserRequest): OidcUser? {
        val clientRegistration = userRequest.clientRegistration
        val oAuth2UserService = OidcUserService()
        val oAuth2User = oAuth2UserService.loadUser(userRequest)
        val providerUserRequest = ProviderUserRequest(clientRegistration, oAuth2User)

        val providerUser = providerUser(providerUserRequest)
        //회원 가입

        super.register(providerUser, userRequest)
        return PrincipalUser(providerUser!!)
    }
}