package me.saechimdaeki.springsecurityoauth2.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.*
import java.util.stream.Collectors


abstract class OAuth2ProviderUser(
    private val attributes: Map<String, Any>,
    private val oAuth2User: OAuth2User,
    private val clientRegistration: ClientRegistration
) :
    ProviderUser {
    override fun getPassword(): String {
        return UUID.randomUUID().toString()
    }

    override fun getEmail(): String {
        return attributes["email"] as String
    }

    override fun getProvider(): String {
        return clientRegistration.registrationId
    }

    override fun getAuthorities(): List<GrantedAuthority> {
        return oAuth2User.authorities.stream().map { authority: GrantedAuthority ->
            SimpleGrantedAuthority(
                authority.authority
            )
        }.collect(Collectors.toList())
    }

    override fun getAttributes(): Map<String, Any> {
       return attributes
    }

    override fun getOAuth2User(): OAuth2User? {
        return oAuth2User
    }
}