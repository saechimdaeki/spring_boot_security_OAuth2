package me.saechimdaeki.resource.config

import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jose.jwk.OctetSequenceKey
import com.nimbusds.jose.jwk.RSAKey
import me.saechimdaeki.resource.filter.authentication.JwtAuthenticationFilter
import me.saechimdaeki.resource.filter.authorization.JwtAuthorizationMacFilter
import me.saechimdaeki.resource.filter.authorization.JwtAuthorizationRsaFilter
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
        http.addFilterBefore(jwtAuthenticationFilter(null,null), UsernamePasswordAuthenticationFilter::class.java)
        http.addFilterBefore(jwtAuthorizationRsaFilter(null),UsernamePasswordAuthenticationFilter::class.java)
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer<*>::jwt)


        return http.build()
    }

/*    @Bean
    fun jwtAuthorizationMacFilter( octetSequenceKey: OctetSequenceKey): JwtAuthorizationMacFilter {
        return JwtAuthorizationMacFilter(MACVerifier(octetSequenceKey.toSecretKey()))
    }*/

    @Bean
    fun jwtAuthorizationRsaFilter( rsaKey: RSAKey?): JwtAuthorizationRsaFilter {
        return JwtAuthorizationRsaFilter(RSASSAVerifier(rsaKey?.toRSAPublicKey()))
    }

    @Bean
    fun jwtAuthenticationFilter(macSecuritySigner: MacSecuritySigner?, octetSequenceKey: OctetSequenceKey?): JwtAuthenticationFilter {
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