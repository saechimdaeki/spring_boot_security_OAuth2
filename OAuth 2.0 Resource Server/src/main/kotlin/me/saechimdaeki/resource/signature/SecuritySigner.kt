package me.saechimdaeki.resource.signature

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSSigner
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.OctetSequenceKey
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.springframework.security.core.userdetails.UserDetails
import java.util.Date

abstract class SecuritySigner {

    abstract fun getToken(user: UserDetails, jwk: JWK?): String
    protected fun getJwtTokenInternal(jwtSigner: JWSSigner, user: UserDetails, jwk: JWK): String {
        val header = JWSHeader.Builder((jwk.algorithm as JWSAlgorithm)).keyID(jwk.keyID).build()
        val jwtClaimsSet = JWTClaimsSet.Builder().subject("user")
            .issuer("http://localhost:8081")
            .claim("username", user.username)
            .claim("authority", user.authorities)
            .expirationTime(Date(Date().time + 60 * 1000 * 5)).build()

        val signedJWT = SignedJWT(header, jwtClaimsSet)
        signedJWT.sign(jwtSigner)
        val jwtToken = signedJWT.serialize()
        return jwtToken
    }
}