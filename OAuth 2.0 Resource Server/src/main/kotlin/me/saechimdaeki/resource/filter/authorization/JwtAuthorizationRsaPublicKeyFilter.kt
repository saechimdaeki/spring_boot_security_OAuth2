package me.saechimdaeki.resource.filter.authorization

import com.nimbusds.jose.JWSVerifier
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.oauth2.jwt.JwtDecoder

class JwtAuthorizationRsaPublicKeyFilter(jwsVerifier: JWSVerifier, private val jwtDecoder: JwtDecoder) : JwtAuthorizationFilter(jwsVerifier) {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        super.doFilterInternal(request, response, filterChain)
    }
}