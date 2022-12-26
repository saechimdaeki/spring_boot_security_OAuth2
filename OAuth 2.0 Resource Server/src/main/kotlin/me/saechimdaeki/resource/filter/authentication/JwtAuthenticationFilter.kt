package me.saechimdaeki.resource.filter.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import com.nimbusds.jose.jwk.JWK
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.saechimdaeki.resource.dto.LoginDto
import me.saechimdaeki.resource.signature.SecuritySigner
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JwtAuthenticationFilter(private val securitySigner: SecuritySigner?, private val jwk: JWK?) :
    UsernamePasswordAuthenticationFilter() {


    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {

        val objectMapper = ObjectMapper()
        val loginDto = objectMapper.readValue(request?.inputStream, LoginDto::class.java)

        val authenticationToken =
            UsernamePasswordAuthenticationToken(loginDto.username, loginDto.password)

        return authenticationManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain?,
        authResult: Authentication
    ) {
        val user = authResult.principal as User
        val jwtToken = securitySigner?.getToken(user, jwk)
        response.addHeader("Authorization", "Bearer " + jwtToken)

    }
}