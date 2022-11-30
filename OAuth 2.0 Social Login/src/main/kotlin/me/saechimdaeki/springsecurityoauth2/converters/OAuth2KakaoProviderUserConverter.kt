package me.saechimdaeki.springsecurityoauth2.converters

import me.saechimdaeki.springsecurityoauth2.enums.OAuth2Config
import me.saechimdaeki.springsecurityoauth2.model.ProviderUser
import me.saechimdaeki.springsecurityoauth2.model.social.KakaoUser
import me.saechimdaeki.springsecurityoauth2.model.social.NaverUser
import me.saechimdaeki.springsecurityoauth2.util.OAuth2Utils


class OAuth2KakaoProviderUserConverter : ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    override fun converter(t: ProviderUserRequest): ProviderUser? {
        return if (!t.clientRegistration?.registrationId.equals(OAuth2Config.SocialType.KAKAO.socialName)) {
            null
        } else KakaoUser(
            OAuth2Utils.getOtherAttributes(
                t.oAuth2User!!, "kakao_account","profile"
            ), t.oAuth2User, t.clientRegistration!!
        )

    }
}