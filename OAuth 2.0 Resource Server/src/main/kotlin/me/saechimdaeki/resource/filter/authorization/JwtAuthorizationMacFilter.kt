package me.saechimdaeki.resource.filter.authorization

import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jose.jwk.OctetSequenceKey
import com.nimbusds.jwt.SignedJWT
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID

class JwtAuthorizationMacFilter(val jwk : OctetSequenceKey) : OncePerRequestFilter() {


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {



        val header = request.getHeader("Authorization")
        if(header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
        }

        val token = header.replace("Bearer ", "")

        val signedJWT = SignedJWT.parse(token)
        val macVerifier = MACVerifier(jwk.toSecretKey())
        val verify = signedJWT.verify(macVerifier)

        if(verify) {
            val jwtClaimsSet = signedJWT.jwtClaimsSet
            val username = jwtClaimsSet.getClaim("username").toString()
            val authority = jwtClaimsSet.getClaim("authority") as List<String>

            val user = User.withUsername(username)
                .password(UUID.randomUUID().toString())
                .authorities(authority.get(0))
                .build()
            val authenticationToken = UsernamePasswordAuthenticationToken(user, null, user.authorities)
            SecurityContextHolder.getContext().authentication = authenticationToken

        }
        filterChain.doFilter(request ,response)

    }
}