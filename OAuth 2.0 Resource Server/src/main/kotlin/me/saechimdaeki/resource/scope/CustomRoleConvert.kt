package me.saechimdaeki.resource.scope

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import java.util.*

class CustomRoleConvert : Converter<Jwt, Collection<GrantedAuthority>> {

    private val PREFIX = "ROLE_"

    override fun convert(jwt: Jwt): Collection<GrantedAuthority>? {

        val scope = jwt.getClaimAsString("scope")
        val realmAccess = jwt.getClaimAsMap("realm_access")

        if (scope == null || realmAccess == null)
            return null

        val authorities1 = scope.split(" ")
            .map { roleName -> PREFIX + roleName }
            .map { role -> SimpleGrantedAuthority(role) }
            .toMutableList()

        val authorities2 = (realmAccess["roles"] as List<String>)
            .map { roleName: String -> PREFIX + roleName }
            .map { role -> SimpleGrantedAuthority(role) }.toList()

        authorities1.addAll(authorities2)
        return authorities1

    }
}