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
        var nameRole = name
        when {
            nameRole.lastIndexOf(".") > 0 -> {
                val index = nameRole.lastIndexOf(".")
                nameRole = "SCOPE_" + nameRole.substring(index + 1)
            }
        }
        when {
            prefix.isNotEmpty() && !nameRole.startsWith(prefix) -> {
                nameRole = prefix + nameRole
            }
        }
        return SimpleGrantedAuthority(nameRole)
    }
}