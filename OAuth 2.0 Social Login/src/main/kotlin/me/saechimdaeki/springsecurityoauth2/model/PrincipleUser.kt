package me.saechimdaeki.springsecurityoauth2.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User


class PrincipalUser(private val providerUser: ProviderUser) : UserDetails, OidcUser, OAuth2User {
    override fun getName(): String {
        return providerUser.getUsername()
    }

    override fun getAttributes(): Map<String, Any> {
        return providerUser.getAttributes()
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return providerUser.getAuthorities()
    }

    override fun getPassword(): String {
        return providerUser.getPassword()
    }

    override fun getUsername(): String {
        return providerUser.getUsername()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getClaims(): Map<String, Any> {
        return emptyMap()
    }

    override fun getUserInfo(): OidcUserInfo? {
        return null
    }

    override fun getIdToken(): OidcIdToken? {
        return null
    }
}