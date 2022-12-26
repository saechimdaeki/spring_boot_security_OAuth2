package me.saechimdaeki.resource.config

import com.nimbusds.jose.jwk.OctetSequenceKey
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder

@Configuration
class JwtDecoderConfig {

    @Bean
    @ConditionalOnProperty(prefix = "spring.security.oauth2.resourceserver.jwt", name = ["jws-algorithms"], havingValue = "HS256", matchIfMissing = false)
    fun jwtDecoderBySecretKeyValue(octetSequenceKey: OctetSequenceKey,properties: OAuth2ResourceServerProperties) : JwtDecoder {
        return NimbusJwtDecoder.withSecretKey(octetSequenceKey.toSecretKey())
            .macAlgorithm(MacAlgorithm.from(properties.jwt.jwsAlgorithms.get(0)))
            .build()
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.security.oauth2.resourceserver.jwt", name = ["jws-algorithms"], havingValue = "RS512", matchIfMissing = false)
    fun jwtDecoderByPublicKeyValue(rsaKey: RSAKey,properties: OAuth2ResourceServerProperties) : JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey())
            .signatureAlgorithm(SignatureAlgorithm.from(properties.jwt.jwsAlgorithms.get(0)))
            .build()
    }
}