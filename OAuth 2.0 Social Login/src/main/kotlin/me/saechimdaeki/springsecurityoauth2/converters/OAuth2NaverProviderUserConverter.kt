package me.saechimdaeki.springsecurityoauth2.converters

import me.saechimdaeki.springsecurityoauth2.enums.OAuth2Config
import me.saechimdaeki.springsecurityoauth2.model.ProviderUser
import me.saechimdaeki.springsecurityoauth2.model.social.NaverUser
import me.saechimdaeki.springsecurityoauth2.util.OAuth2Utils


class OAuth2NaverProviderUserConverter : ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    override fun converter(t: ProviderUserRequest): ProviderUser? {
        return if (!t.clientRegistration?.registrationId.equals(OAuth2Config.SocialType.NAVER.socialName)) {
            null
        } else NaverUser(
            OAuth2Utils.getSubAttributes(
                t.oAuth2User!!, "response"
            ), t.oAuth2User, t.clientRegistration!!
        )

    }
}