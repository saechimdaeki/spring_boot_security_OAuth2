package me.saechimdaeki.springsecurityoauth2.model

import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User

class NaverUser(oAuth2User: OAuth2User, clientRegistration: ClientRegistration) :
    OAuth2ProviderUser(oAuth2User.attributes["response"] as Map<String, Any>, oAuth2User, clientRegistration) {
    override fun getId(): String {
        return getAttributes()["id"] as String
    }

    override fun getUsername(): String {
        return getAttributes()["email"] as String
    }
}