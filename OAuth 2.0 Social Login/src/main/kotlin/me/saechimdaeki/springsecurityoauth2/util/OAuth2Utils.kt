package me.saechimdaeki.springsecurityoauth2.util

import me.saechimdaeki.springsecurityoauth2.model.Attributes
import org.springframework.security.oauth2.core.user.OAuth2User


object OAuth2Utils {
    fun getMainAttributes(oAuth2User: OAuth2User): Attributes {
        return Attributes(mainAttributes = oAuth2User.attributes)
    }

    fun getSubAttributes(oAuth2User: OAuth2User, subAttributesKey: String?): Attributes {
        val subAttributes = oAuth2User.attributes[subAttributesKey] as MutableMap<String, Any>
        return Attributes(subAttributes = subAttributes)
    }

    fun getOtherAttributes(oAuth2User: OAuth2User, subAttributesKey: String?, otherAttributesKey: String): Attributes {
        val subAttributes = oAuth2User.attributes[subAttributesKey] as MutableMap<String, Any>
        val otherAttributes = subAttributes[otherAttributesKey] as MutableMap<String, Any>
        return Attributes(subAttributes = subAttributes, otherAttributes = otherAttributes)
    }
}