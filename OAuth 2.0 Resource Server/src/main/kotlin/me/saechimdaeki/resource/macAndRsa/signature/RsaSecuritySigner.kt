package me.saechimdaeki.resource.macAndRsa.signature

import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.security.core.userdetails.UserDetails

class RsaSecuritySigner : SecuritySigner() {
    override fun getToken(user: UserDetails, jwk: JWK?): String {

        val jwtSigner = RSASSASigner((jwk as RSAKey).toRSAPrivateKey())
        return super.getJwtTokenInternal(jwtSigner, user, jwk)
    }
}