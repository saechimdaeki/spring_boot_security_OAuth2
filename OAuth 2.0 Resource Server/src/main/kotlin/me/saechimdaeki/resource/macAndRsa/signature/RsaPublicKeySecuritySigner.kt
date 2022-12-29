package me.saechimdaeki.resource.macAndRsa.signature

import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.jwk.JWK
import org.springframework.security.core.userdetails.UserDetails
import java.security.PrivateKey

class RsaPublicKeySecuritySigner : SecuritySigner() {

    private lateinit var privateKey : PrivateKey

    override fun getToken(user: UserDetails, jwk: JWK?): String {

        val jwtSigner = RSASSASigner(privateKey)
        return super.getJwtTokenInternal(jwtSigner, user, jwk!!)
    }

    fun setPrivateKey(privateKey: PrivateKey) {
        this.privateKey = privateKey
    }
}