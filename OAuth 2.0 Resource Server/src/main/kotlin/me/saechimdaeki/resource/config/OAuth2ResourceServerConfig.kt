package me.saechimdaeki.resource.config

import me.saechimdaeki.resource.filter.authentication.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class OAuth2ResourceServerConfig(
){

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration?) : AuthenticationManager{
       return authenticationConfiguration!!.authenticationManager //TODO 수정
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf().disable()

        http.authorizeHttpRequests { requests ->
            requests.requestMatchers("/").permitAll()
                .anyRequest().authenticated()
        }
        http.userDetailsService(userDetailsService())
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        val jwtAuthenticationFilter = JwtAuthenticationFilter()
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager(null))
        return jwtAuthenticationFilter
    }

    @Bean
    fun userDetailsService(): UserDetailsService {

        val user = User.withUsername("user").password("1234").authorities("ROLE_USER").build()

        return InMemoryUserDetailsManager(user)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

}