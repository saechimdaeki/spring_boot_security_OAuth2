package me.saechimdaeki.springsecurityoauth2.converters

import me.saechimdaeki.springsecurityoauth2.enums.OAuth2Config
import me.saechimdaeki.springsecurityoauth2.model.ProviderUser
import me.saechimdaeki.springsecurityoauth2.model.social.GoogleUser
import me.saechimdaeki.springsecurityoauth2.util.OAuth2Utils

class OAuth2GoogleProviderUserConverter : ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    override fun converter(t: ProviderUserRequest): ProviderUser? {
        if (t.clientRegistration?.registrationId.equals(OAuth2Config.SocialType.GOOGLE.socialName))
            return null
        return GoogleUser(
            OAuth2Utils.getMainAttributes(
                t.oAuth2User!!,
            ), t.oAuth2User, t.clientRegistration!!
        )
    }
}