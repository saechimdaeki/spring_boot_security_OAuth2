package me.saechimdaeki.resource.macAndRsa.JCA_JCE

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.*
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*
import java.util.List
import java.util.Set
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


object JWKTest {
    fun jwk() {

        // 비대칭키 JWK
        val rsaKeyPairGenerator = KeyPairGenerator.getInstance("RSA")
        rsaKeyPairGenerator.initialize(2048)
        val keyPair = rsaKeyPairGenerator.generateKeyPair()
        val publicKey = keyPair.public as RSAPublicKey
        val privateKey = keyPair.private as RSAPrivateKey

        val rsaKey1: RSAKey = RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyUse(KeyUse.SIGNATURE)
            .algorithm(JWSAlgorithm.RS256)
            .keyID("rsa-kid1")
            .build()
        val rsaKey2 = RSAKeyGenerator(2048)
            .keyID("rsa-kid2")
            .keyUse(KeyUse.SIGNATURE)
            .keyOperations(Set.of(KeyOperation.SIGN))
            .algorithm(JWSAlgorithm.RS512)
            .generate()

        // 대칭키 JWK
        val secretKey: SecretKey = SecretKeySpec(
            Base64.getDecoder().decode("bCzY/M48bbkwBEWjmNSIEPfwApcvXOnkCxORBEbPr+4="), "AES"
        )
        val octetSequenceKey1 = OctetSequenceKey.Builder(secretKey)
            .keyID("secret-kid1")
            .keyUse(KeyUse.SIGNATURE)
            .keyOperations(Set.of(KeyOperation.SIGN))
            .algorithm(JWSAlgorithm.HS256)
            .build()
        val octetSequenceKey2 = OctetSequenceKeyGenerator(256)
            .keyID("secret-kid2")
            .keyUse(KeyUse.SIGNATURE)
            .keyOperations(Set.of(KeyOperation.SIGN))
            .algorithm(JWSAlgorithm.HS384)
            .generate()
        val kId = octetSequenceKey1.keyID
        //        kId = octetSequenceKey2.getKeyID();
        val alg = octetSequenceKey1.algorithm as JWSAlgorithm
        //        alg = (JWSAlgorithm)octetSequenceKey2.getAlgorithm();
//
        val type = KeyType.RSA
        //        type = KeyType.OCT;
        jwkSet(kId, alg, type, rsaKey1, rsaKey2, octetSequenceKey1, octetSequenceKey2)
    }

    private fun jwkSet(kid: String, alg: JWSAlgorithm, type: KeyType, vararg jwk: JWK) {
        val jwkSet = JWKSet(List.of(*jwk))
        val jwkSource: JWKSource<SecurityContext?> =
            JWKSource<SecurityContext?> { jwkSelector: JWKSelector, securityContext: SecurityContext? ->
                jwkSelector.select(
                    jwkSet
                )
            }
        val jwkMatcher = JWKMatcher.Builder()
            .keyType(type)
            .keyID(kid)
            .keyUses(KeyUse.SIGNATURE)
            .algorithms(alg)
            .build()
        val jwkSelector = JWKSelector(jwkMatcher)
        val jwks = jwkSource[jwkSelector, null]
        if (jwks.isNotEmpty()) {
            val jwk1 = jwks[0]
            val keyType: KeyType = jwk1.keyType
            println("keyType = $keyType")
            val keyID = jwk1.keyID
            println("keyID = $keyID")
            val algorithm = jwk1.algorithm
            println("algorithm = $algorithm")
        }
        println("jwks = $jwks")
    }
}