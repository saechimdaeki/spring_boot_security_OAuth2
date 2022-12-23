package me.saechimdaeki.resource.config

import com.nimbusds.jose.jwk.OctetSequenceKey
import me.saechimdaeki.resource.filter.authentication.JwtAuthenticationFilter
import me.saechimdaeki.resource.filter.authorization.JwtAuthorizationMacFilter
import me.saechimdaeki.resource.signature.MacSecuritySigner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
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
    private val macSecuritySigner: MacSecuritySigner,
    private val octetSequenceKey: OctetSequenceKey,
){

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration?) : AuthenticationManager{
       return authenticationConfiguration!!.authenticationManager //TODO 수정
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.authorizeHttpRequests { requests ->
            requests.requestMatchers("/").permitAll()
                .anyRequest().authenticated()
        }
        http.userDetailsService(userDetailsService())
        http.addFilterBefore(jwtAuthenticationFilter(macSecuritySigner,octetSequenceKey), UsernamePasswordAuthenticationFilter::class.java)
        http.addFilterBefore(jwtAuthorizationMacFilter(octetSequenceKey),UsernamePasswordAuthenticationFilter::class.java)
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer<*>::jwt)


        return http.build()
    }

    @Bean
    fun jwtAuthorizationMacFilter( octetSequenceKey: OctetSequenceKey): JwtAuthorizationMacFilter {
        return JwtAuthorizationMacFilter(octetSequenceKey)
    }

    @Bean
    fun jwtAuthenticationFilter(macSecuritySigner: MacSecuritySigner, octetSequenceKey: OctetSequenceKey): JwtAuthenticationFilter {
        val jwtAuthenticationFilter = JwtAuthenticationFilter(macSecuritySigner,octetSequenceKey)
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