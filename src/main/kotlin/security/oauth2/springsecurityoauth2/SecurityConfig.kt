package security.oauth2.springsecurityoauth2

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain1(http: HttpSecurity): SecurityFilterChain {
        http.authorizeRequests().anyRequest().authenticated()
        http.formLogin()
        return http.build()
    }


}