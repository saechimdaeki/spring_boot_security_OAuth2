package me.saechimdaeki.springsecurityoauth2.model

import org.springframework.security.core.GrantedAuthority




interface ProviderUser {

    fun getId():String

    fun getUsername():String

    fun getPassword():String

    fun getEmail() : String

    fun getProvider() :String

    fun getAuthorities(): List<GrantedAuthority>

    fun getAttributes():Map<String, Any>
}