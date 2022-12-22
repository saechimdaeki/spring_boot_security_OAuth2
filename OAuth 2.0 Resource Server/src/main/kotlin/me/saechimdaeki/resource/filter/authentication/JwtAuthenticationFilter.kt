package me.saechimdaeki.resource.filter.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.saechimdaeki.resource.dto.LoginDto
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

class JwtAuthenticationFilter : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {

        val objectMapper = ObjectMapper()
        val loginDto = objectMapper.readValue(request?.inputStream, LoginDto::class.java)

        val authenticationToken =
            UsernamePasswordAuthenticationToken(loginDto.username, loginDto.password)

        return authenticationManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {

        SecurityContextHolder.getContext().authentication = authResult
        successHandler.onAuthenticationSuccess(request,response,authResult)
    }
}