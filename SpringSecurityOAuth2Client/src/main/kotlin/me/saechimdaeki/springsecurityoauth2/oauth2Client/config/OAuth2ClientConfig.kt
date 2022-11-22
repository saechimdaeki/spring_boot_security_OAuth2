package me.saechimdaeki.springsecurityoauth2.oauth2Client.config

import me.saechimdaeki.springsecurityoauth2.oauth2Client.filter.CustomOAuth2LoginAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
class OAuth2ClientConfig(
    private val authorizedClientManger : DefaultOAuth2AuthorizedClientManager,
    private val authroziedClientRepository: OAuth2AuthorizedClientRepository,
) {

    @Bean
    fun oauth2SecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeRequests { requests ->
            requests.antMatchers("/", "/oauth2Login", "/client", "/logout").permitAll().anyRequest().authenticated()
        }
        http.oauth2Client()

        http.logout().invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .clearAuthentication(true)
        http.addFilterBefore(customOAuth2LoginAuthenticationFilter(),UsernamePasswordAuthenticationFilter::class.java)
        return http.build()

    }

    private fun customOAuth2LoginAuthenticationFilter(): CustomOAuth2LoginAuthenticationFilter {
        val customOAuth2LoginAuthenticationFilter =
            CustomOAuth2LoginAuthenticationFilter(authorizedClientManger, authroziedClientRepository)
        customOAuth2LoginAuthenticationFilter.setAuthenticationSuccessHandler { _, response, _ ->
            run {
                response.sendRedirect("/home")
            }
        }
        return customOAuth2LoginAuthenticationFilter;
    }


}