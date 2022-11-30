package me.saechimdaeki.springsecurityoauth2.config

import me.saechimdaeki.springsecurityoauth2.CustomAuthorityMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper

@Configuration
class OAuth2AppConfig {

    @Bean
    fun customAuthorityMapper() : GrantedAuthoritiesMapper {
        return CustomAuthorityMapper()
    }
}