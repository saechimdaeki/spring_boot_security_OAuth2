package me.saechimdaeki.authorizationserver

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.ClientSettings
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings
import java.util.UUID

//@Configuration
//@Import(OAuth2AuthorizationServerConfiguration::class)
class AuthorizationServerConfig1 {

//    @Bean
//    fun providerSettings() : ProviderSettings {
//        return ProviderSettings.builder()
//            .issuer("http://localhost:9000")
//            .build()
//    }
//
//    @Bean
//    fun registeredClientRepository(): RegisteredClientRepository {
//        val registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
//            .clientId("oauth2-client-app")
//            .clientSecret("{noop}secret")
//            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//            .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
//            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//            .redirectUri("http://127.0.0.1:8081/login/oauth2/code/oauth2-client-app")
//            .redirectUri("http://127.0.0.1:8081")
//            .scope(OidcScopes.OPENID)
//            .scope("message.read")
//            .scope("message.write")
//            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//            .build()
//
//        return InMemoryRegisteredClientRepository(registeredClient)
//
//    }
}