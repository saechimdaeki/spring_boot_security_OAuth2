package me.saechimdaeki.resource.scope

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector

class CustomOpaqueTokenIntrospector(private val properties: OAuth2ResourceServerProperties) : OpaqueTokenIntrospector {

    val delegate = NimbusOpaqueTokenIntrospector(
        properties.opaquetoken.introspectionUri,
        properties.opaquetoken.clientId,
        properties.opaquetoken.clientSecret
    )


    override fun introspect(token: String): OAuth2AuthenticatedPrincipal {
        val principal = delegate.introspect(token)
        return DefaultOAuth2AuthenticatedPrincipal(principal.name, principal.attributes, extractAuthorities(principal!!))
    }

    private fun extractAuthorities(principal: OAuth2AuthenticatedPrincipal): MutableCollection<GrantedAuthority>? {

        val scopes = principal.attributes[OAuth2TokenIntrospectionClaimNames.SCOPE] as List<String>
        return scopes.map { scope -> "ROLE_" + scope.uppercase() }
            .map { role -> SimpleGrantedAuthority(role) }
            .toMutableList()
    }
}