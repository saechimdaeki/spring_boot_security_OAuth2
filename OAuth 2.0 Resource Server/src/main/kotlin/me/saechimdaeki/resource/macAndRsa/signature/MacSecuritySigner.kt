package me.saechimdaeki.resource.macAndRsa.signature

import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.OctetSequenceKey
import org.springframework.security.core.userdetails.UserDetails

class MacSecuritySigner : SecuritySigner() {
    override fun getToken(user: UserDetails, jwk: JWK?): String {

        val jwtSigner = MACSigner((jwk as OctetSequenceKey).toSecretKey())
        return super.getJwtTokenInternal(jwtSigner, user , jwk)
    }
}