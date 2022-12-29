package me.saechimdaeki.resource.scope

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.web.SecurityFilterChain

@Configuration
class OAuth2ResourceScopeServer {


    @Bean
    fun securityFilterChainScope1(http: HttpSecurity): SecurityFilterChain {
        http.securityMatcher("/photos/1").authorizeHttpRequests { requests ->
            requests.requestMatchers("/photos/1")
                .hasAuthority("SCOPE_photo")
                .anyRequest().authenticated()
        }
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer<*>::jwt)
        return http.build()
    }

    @Bean
    fun securityFilterChainScope2(http: HttpSecurity): SecurityFilterChain {
        http.securityMatcher("/photos/2").authorizeHttpRequests { requests ->
            requests.requestMatchers("/photos/2")
                .permitAll()
                .anyRequest().authenticated()
        }
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer<*>::jwt)
        return http.build()
    }
}