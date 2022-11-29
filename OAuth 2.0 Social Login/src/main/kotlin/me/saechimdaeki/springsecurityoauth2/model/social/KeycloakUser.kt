package me.saechimdaeki.springsecurityoauth2.model.social

import me.saechimdaeki.springsecurityoauth2.model.OAuth2ProviderUser
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User

class KeycloakUser(oAuth2User: OAuth2User, clientRegistration: ClientRegistration) :
    OAuth2ProviderUser(oAuth2User.attributes, oAuth2User, clientRegistration) {
    override fun getId(): String {
        return getAttributes()["sub"] as String
    }

    override fun getUsername(): String {
        return getAttributes()["preferred_username"] as String
    }
}