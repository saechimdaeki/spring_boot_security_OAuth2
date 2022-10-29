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
//        http.apply(CustomSecurityConfigurer().apply {
//            setFlag(true)
//        })
        return http.build()
    }


    @Bean
    fun securityFilterChain2(http: HttpSecurity): SecurityFilterChain {
        http.authorizeRequests().anyRequest().authenticated()
        http.httpBasic()
        return http.build()
    }
}