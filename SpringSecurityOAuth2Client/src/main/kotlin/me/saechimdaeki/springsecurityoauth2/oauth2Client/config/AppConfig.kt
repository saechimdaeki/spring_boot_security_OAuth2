package me.saechimdaeki.springsecurityoauth2.oauth2Client.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.util.StringUtils
import java.time.Duration
import java.util.function.Function
import javax.servlet.http.HttpServletRequest


@Configuration

class AppConfig {

    @Bean
    fun oAuth2AuthorizedClientManager(
        clientRegistrationRepository: ClientRegistrationRepository,
        clientrepository: OAuth2AuthorizedClientRepository
    ): OAuth2AuthorizedClientManager {
        val oAuth2AuthorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
            .authorizationCode()
            .password{passwordGrantBuilder -> passwordGrantBuilder.clockSkew(Duration.ofSeconds(3600))}
            .clientCredentials()
            .refreshToken{refreshTokenGrantBuilder -> refreshTokenGrantBuilder.clockSkew(Duration.ofSeconds(3600))}
            .build()

        val oAuth2AuthorizedClientManager =
            DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, clientrepository)

        oAuth2AuthorizedClientManager.setAuthorizedClientProvider(oAuth2AuthorizedClientProvider)
        oAuth2AuthorizedClientManager.setContextAttributesMapper(contextAttributesMapper())
        return oAuth2AuthorizedClientManager
    }

    private fun contextAttributesMapper(): Function<OAuth2AuthorizeRequest, Map<String, Any>>? {
        return Function { oAuth2AuthorizeRequest: OAuth2AuthorizeRequest ->
            val contextAttributes: MutableMap<String, Any> = HashMap()
            val request =
                oAuth2AuthorizeRequest.getAttribute<HttpServletRequest>(HttpServletRequest::class.java.name)
            val username = request!!.getParameter(OAuth2ParameterNames.USERNAME)
            val password = request.getParameter(OAuth2ParameterNames.PASSWORD)
            if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
                contextAttributes[OAuth2AuthorizationContext.USERNAME_ATTRIBUTE_NAME] = username
                contextAttributes[OAuth2AuthorizationContext.PASSWORD_ATTRIBUTE_NAME] = password
            }
            contextAttributes
        }
    }
}