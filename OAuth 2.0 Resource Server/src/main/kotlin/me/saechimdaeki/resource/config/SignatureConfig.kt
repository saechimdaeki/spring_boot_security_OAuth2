package me.saechimdaeki.resource.config

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.OctetSequenceKey
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator
import me.saechimdaeki.resource.signature.MacSecuritySigner
import me.saechimdaeki.resource.signature.RsaSecuritySigner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SignatureConfig {


    // --- 대칭키 ---- //
    @Bean
    fun macSecuritySigner() : MacSecuritySigner {
        return MacSecuritySigner()
    }

    @Bean
    fun octetSequenceKey(): OctetSequenceKey {
        val octetSequenceKey = OctetSequenceKeyGenerator(256)
            .keyID("macKey")
            .algorithm(JWSAlgorithm.HS256)
            .generate()
        return octetSequenceKey
    }

    // ---- 비대칭 키 ----  //

    @Bean
    fun rsaSecuritySigner() : RsaSecuritySigner {
        return RsaSecuritySigner()
    }

    @Bean
    fun rsaKey() : RSAKey {
        val rsaKey = RSAKeyGenerator(2048)
            .keyID("rsaKey")
            .algorithm(JWSAlgorithm.RS256)
            .generate()
        return rsaKey
    }
}