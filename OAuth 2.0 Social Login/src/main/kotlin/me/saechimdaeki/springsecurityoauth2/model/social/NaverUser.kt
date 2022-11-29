package me.saechimdaeki.springsecurityoauth2.model.social

import me.saechimdaeki.springsecurityoauth2.model.Attributes
import me.saechimdaeki.springsecurityoauth2.model.OAuth2ProviderUser
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User

class NaverUser(attributes: Attributes, oAuth2User: OAuth2User, clientRegistration: ClientRegistration) :
    OAuth2ProviderUser(attributes.subAttributes, oAuth2User, clientRegistration) {
    override fun getId(): String {
        return getAttributes()["id"] as String
    }

    override fun getUsername(): String {
        return getAttributes()["email"] as String
    }
}