package me.saechimdaeki.authorizationserver

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class DefaultSecurityConfig {

    @Bean
    fun securityFilterChain(http:HttpSecurity) : SecurityFilterChain {

        http.authorizeRequests { req -> req.anyRequest().authenticated() }
        http.formLogin()

        return http.build()
    }

    @Bean
    fun userDetailsService() :UserDetailsService {
        val user = User.withUsername("user").password("{noop}1234").authorities("ROLE_USER").build()
        return InMemoryUserDetailsManager(user)
    }
}