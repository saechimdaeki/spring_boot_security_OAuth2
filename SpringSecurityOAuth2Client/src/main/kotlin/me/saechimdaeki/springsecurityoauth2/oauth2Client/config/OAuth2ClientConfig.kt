package me.saechimdaeki.springsecurityoauth2.oauth2Client.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
class OAuth2ClientConfig {

    @Bean
    fun oauth2SecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeRequests { requests ->
            requests.antMatchers("/", "/oauth2Login", "/client", "/logout").permitAll().anyRequest().authenticated()
        }
        http.oauth2Client()

        http.logout().invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .clearAuthentication(true)
        return http.build()

    }
}