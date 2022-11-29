package me.saechimdaeki.springsecurityoauth2.converters

import me.saechimdaeki.springsecurityoauth2.model.users.User
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User

data class ProviderUserRequest(
    val clientRegistration: ClientRegistration?,
    val oAuth2User: OAuth2User?,
    val user: User?
) {
    constructor(clientRegistration: ClientRegistration, oAuth2User: OAuth2User) : this(
        clientRegistration,
        oAuth2User,
        null
    )

    constructor(user: User) : this(
        null,
        null,
        user
    )
}