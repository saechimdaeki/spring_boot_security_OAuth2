package me.saechimdaeki.authorizationserver

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.ClientSettings
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.time.Instant
import java.util.*

@Configuration
class AppConfig {


    @Bean
    fun providerSettings(): ProviderSettings {
        return ProviderSettings.builder().issuer("http://localhost:9000").build()
    }

    @Bean
    fun registeredClientRepository(): RegisteredClientRepository {
        val registeredClient1 = getRegisteredClient("oauth2-client-app1", "{noop}secret1", "read", "write")
        val registeredClient2 = getRegisteredClient("oauth2-client-app2", "{noop}secret2", "read", "delete")
        val registeredClient3 = getRegisteredClient("oauth2-client-app3", "{noop}secret3", "read", "update")

        return InMemoryRegisteredClientRepository(listOf(registeredClient1, registeredClient2, registeredClient3))

    }

    fun getRegisteredClient(clientId: String, clientSecret: String, scope1: String, scope2: String): RegisteredClient {
        return RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId(clientId)
            .clientSecret(clientSecret)
            .clientIdIssuedAt(Instant.now())
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .redirectUri("http://127.0.0.1:8081")
            .scope(OidcScopes.OPENID)
            .scope(scope1)
            .scope(scope2)
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .build()
    }


    @Bean
    fun jwkSource(): JWKSource<SecurityContext> {

        val rsaKey = generateRsa()

        val jwkSet = JWKSet(rsaKey)
        return JWKSource { jwkSelector, _ -> jwkSelector.select(jwkSet) }
    }

    @Bean
    fun jwtDecoder(jwkSource: JWKSource<SecurityContext>): JwtDecoder {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)
    }

    private fun generateRsa(): RSAKey {

        val keyPair: KeyPair = generateRsaKey()

        val rsaPrivateKey = keyPair.private as RSAPrivateKey
        val rsaPublicKey = keyPair.public as RSAPublicKey

        return RSAKey.Builder(rsaPublicKey)
            .privateKey(rsaPrivateKey)
            .keyID(UUID.randomUUID().toString())
            .build()
    }

    private fun generateRsaKey(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        return keyPairGenerator.generateKeyPair()
    }
}