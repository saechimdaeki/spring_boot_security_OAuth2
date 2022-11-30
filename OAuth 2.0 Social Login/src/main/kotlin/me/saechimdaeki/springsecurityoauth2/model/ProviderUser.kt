package me.saechimdaeki.springsecurityoauth2.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User


interface ProviderUser {

    fun getId():String

    fun getUsername():String

    fun getPassword():String

    fun getEmail() : String

    fun getProvider() :String

    fun getPicture() : String

    fun getAuthorities(): List<GrantedAuthority>

    fun getAttributes():Map<String, Any>

    fun getOAuth2User(): OAuth2User?
}