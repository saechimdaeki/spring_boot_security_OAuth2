package me.saechimdaeki.springsecurityoauth2.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

data class FormUser(
    val formUserId: String,
    val formUserName: String,
    val formPassword: String,
    val formEmail: String,
    val formProvider:String,
    val formAuthorities: List<GrantedAuthority>

) : ProviderUser {

    override fun getId(): String {
        return formUserId
    }

    override fun getUsername(): String {
        return formUserName
    }

    override fun getPassword(): String {
        return formPassword
    }

    override fun getEmail(): String {
        return formPassword
    }

    override fun getProvider(): String {
        return ""
    }

    override fun getPicture(): String {
        return ""
    }

    override fun getAuthorities(): List<GrantedAuthority> {
        return formAuthorities
    }

    override fun getAttributes(): Map<String, Any> {
        return emptyMap()
    }

    override fun getOAuth2User(): OAuth2User? {
        return null
    }
}