package me.saechimdaeki.springsecurityoauth2.service

import me.saechimdaeki.springsecurityoauth2.repository.UserRepository
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser

class CustomOidcUserService(userRepository: UserRepository, userService: UserService) : AbstractOAuth2UserService(
    userRepository,
    userService
), OAuth2UserService<OidcUserRequest, OidcUser> {
    override fun loadUser(userRequest: OidcUserRequest): OidcUser? {
        val clientRegistration = userRequest.clientRegistration
        val oAuth2UserService = OidcUserService()
        val oAuth2User = oAuth2UserService.loadUser(userRequest)
        val providerUser = super.providerUser(clientRegistration, oAuth2User)

        //회원 가입

        super.register(providerUser, userRequest)
        return oAuth2User
    }
}