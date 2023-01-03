package me.saechimdaeki.oauth2client

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.client.RestTemplate

@Configuration
@EnableWebSecurity
class OAuth2ClientConfig {


    @Bean
    fun securityFilterChain(http: HttpSecurity) : SecurityFilterChain {
        http.authorizeHttpRequests { request -> request.requestMatchers("/").permitAll()
            .anyRequest().authenticated()
        }
        http.oauth2Login { authLogin -> authLogin.defaultSuccessUrl("/") }
        return http.build()
    }

    @Bean
    fun restTemplate() : RestTemplate {
        return RestTemplate()
    }


}