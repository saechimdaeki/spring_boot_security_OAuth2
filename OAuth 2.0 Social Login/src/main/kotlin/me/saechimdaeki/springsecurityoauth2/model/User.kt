package me.saechimdaeki.springsecurityoauth2.model

import org.springframework.security.core.GrantedAuthority

data class User(
    val registrationId:String? = null,
    val id:String? = null,
    val username:String? = null,
    val password:String? = null,
    val provider:String? = null,
    val email:String? = null,
    val authorities:List<GrantedAuthority>? = null
) {
}