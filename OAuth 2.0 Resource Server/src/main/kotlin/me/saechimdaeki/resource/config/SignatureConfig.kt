package me.saechimdaeki.resource.config

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.OctetSequenceKey
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator
import me.saechimdaeki.resource.signature.MacSecuritySigner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SignatureConfig {

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
}