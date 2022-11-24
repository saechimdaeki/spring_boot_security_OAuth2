package me.saechimdaeki.springsecurityoauth2

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper


class CustomAuthorityMapper : GrantedAuthoritiesMapper {
    private val prefix = "ROLE_"
    override fun mapAuthorities(authorities: Collection<GrantedAuthority>): Set<GrantedAuthority> {
        val mapped = HashSet<GrantedAuthority>(authorities.size)
        authorities.mapTo(mapped) { mapAuthority(it.authority) }
        return mapped
    }

    private fun mapAuthority(name: String): GrantedAuthority {
        var name = name
        if (name.lastIndexOf(".") > 0) {
            val index = name.lastIndexOf(".")
            name = "SCOPE_" + name.substring(index + 1)
        }
        if (prefix.isNotEmpty() && !name.startsWith(prefix)) {
            name = prefix + name
        }
        return SimpleGrantedAuthority(name)
    }
}